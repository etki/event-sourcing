package me.etki.es.engine;

import me.etki.es.EntityDirector;
import me.etki.es.EventStream;
import me.etki.es.Transition;
import me.etki.es.concurrent.CompletableFutures;
import me.etki.es.container.Entity;
import me.etki.es.container.EntityDirectorSettings;
import me.etki.es.container.EntityId;
import me.etki.es.container.EntityType;
import me.etki.es.container.Event;
import me.etki.es.container.Snapshot;
import me.etki.es.exception.UnregisteredEntityException;
import me.etki.es.exception.UnregisteredTransitionException;
import me.etki.es.listener.EntityListener;
import me.etki.es.listener.WildcardListener;
import me.etki.es.store.EventStorage;
import me.etki.es.store.SnapshotStorage;
import me.etki.es.store.StoredEntity;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class StandardEntityDirector implements EntityDirector {

    private final Map<Class, Set<EntityListener>> entityListeners = new ConcurrentHashMap<>();
    private final Set<WildcardListener> wildcardListeners = Collections.newSetFromMap(new ConcurrentHashMap<>());

    private final EventStorage events;
    private final SnapshotStorage snapshots;
    private final EntityRegistry registry;
    private final EntityDirectorSettings settings;

    public StandardEntityDirector(
            EventStorage events,
            SnapshotStorage snapshots,
            EntityRegistry registry,
            EntityDirectorSettings settings) {

        this.events = events;
        this.snapshots = snapshots;
        this.registry = registry;
        this.settings = settings;
    }

    @Override
    public <E, ID> CompletableFuture<Boolean> append(
            Class<E> entityClass,
            ID entityId,
            Transition<E, ID> transition,
            ZonedDateTime occurredAt,
            ZonedDateTime acknowledgedAt)
            throws UnregisteredEntityException, UnregisteredTransitionException {

        EntityType<E> type = getEntityType(entityClass);
        EntityId<ID> id = getEntityId(entityClass, entityId);
        return events.append(type, id, transition, occurredAt, acknowledgedAt);
    }

    @Override
    public <E, ID> CompletableFuture<Entity<E, ID>> get(Class<E> entityClass, ID entityId)
            throws UnregisteredEntityException {

        EntityType<E> type = getEntityType(entityClass);
        EntityId<ID> id = getEntityId(entityClass, entityId);
        return snapshots
                .getLast(type, id)
                .thenCompose(snapshot -> restoreFromSnapshot(snapshot, type, id, Long.MAX_VALUE));
    }

    @Override
    public <E, ID> CompletableFuture<Entity<E, ID>> getAt(Class<E> entityClass, ID entityId, long version)
            throws UnregisteredEntityException {

        EntityType<E> type = getEntityType(entityClass);
        EntityId<ID> id = getEntityId(entityClass, entityId);
        return snapshots
                .getLastUpTo(type, id, version)
                .thenCompose(snapshot -> restoreFromSnapshot(snapshot, type, id, version));
    }

    private <E, ID> CompletableFuture<Entity<E, ID>> restoreFromSnapshot(
            Snapshot<E, ID> snapshot,
            EntityType<E> entityType,
            EntityId<ID> entityId,
            long version) {

        Snapshot<E, ID> reference = snapshot == null ? new Snapshot<E, ID>().setEntityVersion(0) : snapshot;
        return events
                .getSlice(entityType, entityId, reference.getEntityVersion() + 1, version)
                .thenApply(events -> {
                    if (reference.getEntityVersion() == 0 && events.isEmpty()) {
                        return null;
                    }
                    Entity<E, ID> result = new Entity<E, ID>()
                            .setId(entityId)
                            .setType(entityType)
                            .setVersion(reference.getEntityVersion() > 0 ? reference.getEntityVersion() : null)
                            .setSnapshotVersion(reference.getEntityVersion() > 0 ? reference.getEntityVersion() : null)
                            .setTransitionedAt(reference.getTransitionedAt())
                            .setTransitionAcknowledgedAt(reference.getTransitionAcknowledgedAt())
                            .setSnapshotTakenAt(reference.getTakenAt());
                    E entity = reference.getEntity();
                    for (Event<E, ID> event : events) {
                        entity = event.apply(entity);
                        result
                                .setTransitionedAt(event.getOccurredAt())
                                .setTransitionAcknowledgedAt(event.getAcknowledgedAt());
                    }
                    return result.setEntity(entity);
                })
                .thenCompose(entity -> {
                    CompletableFuture<Void> snapshotFuture = CompletableFutures.VOID;
                    long snapshotVersion = entity.getSnapshotVersion() == null ? 0 : entity.getSnapshotVersion();
                    long entityVersion = entity.getVersion() == null ? 0 : entity.getVersion();
                    if (entityVersion - snapshotVersion < settings.getSnapshotThreshold()) {
                        snapshotFuture = snapshot(entityType.getEntityClass(), entityId.getId(), entityVersion);
                    }
                    if (settings.shouldAwaitSnapshotGeneration()) {
                        return snapshotFuture.thenApply(v -> entity);
                    }
                    return CompletableFutures.completed(entity);
                });
    }

    @Override
    public <E, ID> EventStream<E, ID> getEventStream(Class<E> entityClass, ID entityId) throws UnregisteredEntityException {
        return events.getStream(getEntityType(entityClass), getEntityId(entityClass, entityId));
    }

    @Override
    public <E, ID> CompletableFuture<Void> purge(Class<E> entityClass, ID entityId)
            throws UnregisteredEntityException, UnsupportedOperationException {

        if (!events.supportsPurge()) {
            throw new UnsupportedOperationException("Current event store doesn't support purge functionality");
        }
        if (!snapshots.supportsPurge()) {
            throw new UnsupportedOperationException("Current snapshot store doesn't support purge functionality");
        }
        EntityType<E> type = getEntityType(entityClass);
        EntityId<ID> id = getEntityId(entityClass, entityId);
        return CompletableFuture.allOf(events.purge(type, id), snapshots.purge(type, id));
    }

    @Override
    public <E, ID> CompletableFuture<Void> snapshot(Class<E> entityClass, ID entityId, long index)
            throws UnregisteredEntityException {

        return getAt(entityClass, entityId, index)
                .thenCompose(entity -> {
                    if (entity == null || Long.valueOf(index).equals(entity.getSnapshotVersion())) {
                        return CompletableFutures.VOID;
                    }
                    Snapshot<E, ID> snapshot = new Snapshot<E, ID>()
                            .setEntityType(getEntityType(entityClass))
                            .setEntityId(getEntityId(entityClass, entityId))
                            .setEntityVersion(entity.getVersion())
                            .setEntity(entity.getEntity())
                            .setTransitionedAt(entity.getTransitionedAt())
                            .setTransitionAcknowledgedAt(entity.getTransitionAcknowledgedAt())
                            .setTakenAt(ZonedDateTime.now());
                    return snapshots.save(snapshot);
                });
    }

    @Override
    public <E, ID> CompletableFuture<Void> deleteSnapshot(Class<E> entityClass, ID entityId, long index)
            throws UnregisteredEntityException, UnsupportedOperationException {

        return snapshots.delete(getEntityType(entityClass), getEntityId(entityClass, entityId), index);
    }

    @Override
    public CompletableFuture<List<StoredEntity>> getStoredEntities(long skip, long size)
            throws UnsupportedOperationException {

        if (events.supportsBrowsing()) {
            String message = "Current event storage implementation doesn't provide entity browsing functionality";
            throw new UnsupportedOperationException(message);
        }
        return events.getStoredEntities(skip, size);
    }

    @Override
    public CompletableFuture<Long> countStoredEntities() throws UnsupportedOperationException {
        if (!events.supportsBrowsing()) {
            String message = "Current event storage implementation doesn't provide entity browsing functionality";
            throw new UnsupportedOperationException(message);
        }
        return events.countStoredEntities();
    }

    private <E> EntityType<E> getEntityType(Class<E> entityClass) {
        return registry.getDescriptor(entityClass).getEntityType();
    }
    
    private <E, ID> EntityId<ID> getEntityId(Class<E> entityClass, ID entityId) {
        String encodedId = registry.<E, ID>getDescriptor(entityClass).getIdentifierConverter().encode(entityId);
        return EntityId.of(entityId, encodedId);
    }


    @Override
    public boolean supportsBrowsing() {
        return events.supportsBrowsing();
    }

    @Override
    public boolean supportsPurge() {
        return events.supportsPurge() && snapshots.supportsPurge();
    }

    @Override
    public boolean supportsSnapshotDeletion() {
        return snapshots.supportsPurge();
    }

    @Override
    public <E, ID> EntityDirector addEntityListener(Class<E> entityClass, EntityListener<E, ID> listener) {
        Set<EntityListener> listeners = entityListeners
                .computeIfAbsent(entityClass, v -> Collections.newSetFromMap(new ConcurrentHashMap<>()));
        listeners.add(listener);
        return this;
    }

    @Override
    public <E, ID> EntityDirector removeEntityListener(Class<E> entityClass, EntityListener<E, ID> listener) {
        Set<EntityListener> listeners = entityListeners.get(entityClass);
        if (listeners != null) {
            listeners.remove(listener);
        }
        return this;
    }

    @Override
    public EntityDirector addEntityListener(WildcardListener listener) {
        wildcardListeners.add(listener);
        return this;
    }

    @Override
    public EntityDirector removeEntityListener(WildcardListener listener) {
        wildcardListeners.remove(listener);
        return this;
    }
}
