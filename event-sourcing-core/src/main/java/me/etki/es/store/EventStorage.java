package me.etki.es.store;

import me.etki.es.DeprecatedTransition;
import me.etki.es.EventStream;
import me.etki.es.Transition;
import me.etki.es.concurrent.CompletableFutures;
import me.etki.es.container.EntityId;
import me.etki.es.container.EntityType;
import me.etki.es.container.Event;
import me.etki.es.container.TransitionTypeDescriptor;
import me.etki.es.engine.EntityRegistry;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EventStorage {

    private final SerializedEventStore store;
    private final PurgingEventStore purgingStore;
    private final BrowseableEventStore browseableStore;
    private final ReplacingEventStore replacingStore;

    private final TransitionSerializer serializer;
    private final EntityRegistry registry;
    private final EventStorageSettings settings;

    public EventStorage(
            SerializedEventStore store,
            TransitionSerializer serializer,
            EntityRegistry registry,
            EventStorageSettings settings) {

        this.store = store;
        this.serializer = serializer;
        this.registry = registry;
        this.settings = settings;

        replacingStore = store instanceof ReplacingEventStore ? (ReplacingEventStore) store : null;
        browseableStore = store instanceof BrowseableEventStore ? (BrowseableEventStore) store : null;
        purgingStore = store instanceof PurgingEventStore ? (PurgingEventStore) store : null;
    }

    public <E, ID> CompletableFuture<Long> count(EntityType<E> entityType, EntityId<ID> entityId) {
        return store.count(entityType.getEntityType(), entityId.getEncodedId());
    }

    public <E, ID> CompletableFuture<Long> getMaxEventNumber(EntityType<E> entityType, EntityId<ID> entityId) {
        return store.getMaxEventNumber(entityType.getEntityType(), entityId.getEncodedId());
    }

    public <E, ID> CompletableFuture<List<Event<E, ID>>> getSlice(
            EntityType<E> entityType,
            EntityId<ID> entityId,
            long startIndex,
            long endIndex) {

        return store
                .getSlice(entityType.getEntityType(), entityId.getEncodedId(), startIndex, endIndex)
                .thenCompose(serializer::<E, ID>deserializeAll)
                .thenCompose(events -> {
                    List<CompletableFuture<Void>> futures = events.stream()
                            .map(this::repair)
                            .collect(Collectors.toList());
                    return CompletableFuture
                            .allOf(futures.toArray(new CompletableFuture[0]))
                            .thenApply(v -> events);
                });
    }

    private <E, ID> CompletableFuture<Void> repair(Event<E, ID> event) {
        if (!(event.getTransition() instanceof DeprecatedTransition) || !settings.getAutomaticRepair()) {
            return CompletableFutures.VOID;
        }
        if (!supportsReplacement()) {
            // todo: leave log entry
            return CompletableFutures.VOID;
        }
        Transition<E, ID> upgraded = ((DeprecatedTransition<E, ID>) event.getTransition()).upgrade();
        @SuppressWarnings("unchecked") // safe by design
        Class<? extends Transition<E, ID>> transitionClass = (Class<? extends Transition<E, ID>>) upgraded.getClass();
        TransitionTypeDescriptor<E, ID> transitionType = registry
                .<E, ID>getDescriptor(event.getEntityType().getEntityClass())
                .getTransitionType(transitionClass);
        event
                .setTransitionType(transitionType)
                .setTransition(upgraded);
        return replace(event).thenApply(v -> null);
    }

    public <E, ID> CompletableFuture<Boolean> append(
            EntityType<E> entityType,
            EntityId<ID> entityId,
            Transition<E, ID> transition,
            ZonedDateTime occurredAt,
            ZonedDateTime acknowledgedAt) {

        return getMaxEventNumber(entityType, entityId)
                .thenApply(maxEventNumber ->
                        new Event<E, ID>()
                                .setEntityType(entityType)
                                .setEntityId(entityId)
                                .setIndex(maxEventNumber + 1)
                                .setTransition(transition)
                                .setTransitionType(getTransitionType(entityType, transition))
                                .setOccurredAt(occurredAt)
                                .setAcknowledgedAt(acknowledgedAt)
                )
                .thenCompose(serializer::serialize)
                .thenCompose(store::insert);
    }

    public <E, ID> CompletableFuture<Boolean> append(
            EntityType<E> entityType,
            EntityId<ID> entityId,
            Transition<E, ID> transition,
            ZonedDateTime occurredAt) {

        return append(entityType, entityId, transition, occurredAt, ZonedDateTime.now());
    }

    public <E, ID> CompletableFuture<Boolean> replace(Event<E, ID> event) {

        if (!supportsReplacement()) {
            throw new UnsupportedOperationException("Store " + store + " doesn't support replacement");
        }
        ReplacingEventStore replacingEventStore = (ReplacingEventStore) store;
        return serializer.serialize(event).thenCompose(replacingEventStore::replace);
    }

    public <E, ID> EventStream<E, ID> getStream(EntityType<E> entityType, EntityId<ID> entityId) {
        return new DefaultEventStream<>(entityType, entityId);
    }

    public <E, ID> CompletableFuture<Void> purge(EntityType<E> entityType, EntityId<ID> entityId) {
        assertSupportsPurge();
        return purgingStore.purge(entityType.getEntityType(), entityId.getEncodedId());
    }

    public CompletableFuture<List<StoredEntity>> getStoredEntities(long skip, long size) {
        assertSupportsBrowsing();
        return browseableStore.getStoredEntities(skip, size);
    }

    public CompletableFuture<Long> countStoredEntities() {
        assertSupportsBrowsing();
        return browseableStore.countStoredEntities();
    }

    public boolean supportsReplacement() {
        return replacingStore != null;
    }

    public boolean supportsBrowsing() {
        return browseableStore != null;
    }

    public boolean supportsPurge() {
        return purgingStore != null;
    }

    private void assertSupportsPurge() {
        if (!supportsPurge()) {
            throw new UnsupportedOperationException("Store " + store + " doesn't support purging");
        }
    }

    private void assertSupportsBrowsing() {
        if (!supportsBrowsing()) {
            throw new UnsupportedOperationException("Store " + store + " doesn't support browsing");
        }
    }

    private void assertSupportsReplacement() {
        if (!supportsReplacement()) {
            throw new UnsupportedOperationException("Store " + store + " doesn't support replacement");
        }
    }

    private <E, ID>TransitionTypeDescriptor<E, ID> getTransitionType(
            EntityType<E> entityType,
            Transition<E, ID> transition) {

        @SuppressWarnings("unchecked") // safe by design
        Class<? extends Transition<E, ID>> transitionClass = (Class<? extends Transition<E, ID>>) transition.getClass();
        return registry.<E, ID>getDescriptor(entityType.getEntityClass()).getTransitionType(transitionClass);
    }

    // todo: this stream may cache up to N retrieved events to speed up calls for same event ranges
    private class DefaultEventStream<E, ID> implements EventStream<E, ID> {

        private final EntityType<E> entityType;
        private final EntityId<ID> entityId;

        public DefaultEventStream(EntityType<E> entityType, EntityId<ID> entityId) {
            this.entityType = entityType;
            this.entityId = entityId;
        }

        @Override
        public CompletableFuture<List<Event<E, ID>>> get(long skip, long size) {
            return EventStorage.this.getSlice(entityType, entityId, skip, size);
        }

        @Override
        public CompletableFuture<Long> count() {
            return EventStorage.this.count(entityType, entityId);
        }

        @Override
        public CompletableFuture<Boolean> append(Transition<E, ID> transition, ZonedDateTime occurredAt) {
            return EventStorage.this.append(entityType, entityId, transition, occurredAt);
        }
    }
}
