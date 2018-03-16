package me.etki.es.store;

import me.etki.es.container.EntityId;
import me.etki.es.container.EntityType;
import me.etki.es.container.Snapshot;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class SnapshotStorage {

    private final SerializedSnapshotStore store;
    private final SnapshotSerializer serializer;

    private final PurgingSnapshotStore purgingStore;

    public SnapshotStorage(SerializedSnapshotStore store, SnapshotSerializer serializer) {
        this.store = store;
        this.serializer = serializer;

        purgingStore = store instanceof PurgingEventStore ? (PurgingSnapshotStore) store : null;
    }

    public <E, ID> CompletableFuture<Long> count(EntityType<E> entityType, EntityId<ID> entityId) {
        return store.count(entityType.getEntityType(), entityId.getEncodedId());
    }

    public <E, ID> CompletableFuture<Snapshot<E, ID>> get(EntityType<E> entityType, EntityId<ID> entityId, long version) {
        return store
                .get(entityType.getEntityType(), entityId.getEncodedId(), version)
                .thenCompose(serializer::deserialize);
    }

    public <E, ID> CompletableFuture<List<Snapshot<E, ID>>> getSlice(
            EntityType<E> entityType, EntityId<ID> entityId,
            long skip,
            long size) {

        return store
                .getSlice(entityType.getEntityType(), entityId.getEncodedId(), skip, size)
                .thenCompose(serializer::deserializeAll);
    }

    public <E, ID> CompletableFuture<Snapshot<E, ID>> getLast(EntityType<E> entityType, EntityId<ID> entityId) {
        return store
                .getLast(entityType.getEntityType(), entityId.getEncodedId())
                .thenCompose(serializer::deserialize);
    }

    public <E, ID> CompletableFuture<Snapshot<E, ID>> getLastUpTo(
            EntityType<E> entityType,
            EntityId<ID> entityId,
            long version) {

        return store
                .getLastUpTo(entityType.getEntityType(), entityId.getEncodedId(), version)
                .thenCompose(serializer::deserialize);
    }


    public <E, ID> CompletableFuture<Void> save(Snapshot<E, ID> snapshot) {
        return serializer
                .serialize(snapshot)
                .thenCompose(store::save);
    }

    public <E, ID> CompletableFuture<Void> delete(EntityType<E> entityType, EntityId<ID> entityId, long version) {
        assertSupportsPurge();
        return purgingStore.delete(entityType.getEntityType(), entityId.getEncodedId(), version);
    }

    public <E, ID> CompletableFuture<Void> purge(EntityType<E> entityType, EntityId<ID> entityId) {
        assertSupportsPurge();
        return purgingStore.purge(entityType.getEntityType(), entityId.getEncodedId());
    }

    public boolean supportsPurge() {
        return purgingStore != null;
    }

    private void assertSupportsPurge() {
        if (!supportsPurge()) {
            throw new UnsupportedOperationException("Snapshot store " + store + " doesn't support purge functionality");
        }
    }
}
