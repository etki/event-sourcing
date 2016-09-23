package me.etki.es.storage;

import me.etki.es.EntityDescriptor;
import me.etki.es.EntityRegistry;
import me.etki.es.Snapshot;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class SnapshotStorage {

    private final SerializedSnapshotStorage storage;
    private final SnapshotSerializer serializer;
    private final EntityRegistry registry;

    public SnapshotStorage(SerializedSnapshotStorage storage, SnapshotSerializer serializer, EntityRegistry registry) {
        this.storage = storage;
        this.serializer = serializer;
        this.registry = registry;
    }

    public <E, ID> CompletableFuture<Long> count(Class<E> entityClass, ID id) {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(entityClass);
        return storage.count(descriptor.getEntityType(), descriptor.getIdentifierConverter().encode(id));
    }

    public <E, ID> CompletableFuture<Snapshot<E, ID>> get(Class<E> entityClass, ID id, long version) {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(entityClass);
        return storage
                .get(descriptor.getEntityType(), descriptor.getIdentifierConverter().encode(id), version)
                .thenCompose(serializer::deserialize);
    }

    public <E, ID> CompletableFuture<List<Snapshot<E, ID>>> getSlice(Class<E> entityClass, ID id, long skip, long size) {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(entityClass);
        return storage
                .getSlice(descriptor.getEntityType(), descriptor.getIdentifierConverter().encode(id), skip, size)
                .thenCompose(serializer::deserializeAll);
    }

    public <E, ID> CompletableFuture<Snapshot<E, ID>> getLastBefore(Class<E> entityClass, ID id, long version) {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(entityClass);
        return storage
                .getLast(descriptor.getEntityType(), descriptor.getIdentifierConverter().encode(id))
                .thenCompose(serializer::deserialize);
    }


    public <E, ID> CompletableFuture<Void> save(Snapshot<E, ID> snapshot) {
        return serializer
                .serialize(snapshot)
                .thenCompose(storage::save);
    }

    public <E, ID> CompletableFuture<Void> delete(Class<E> entityClass, ID id, long version) {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(entityClass);
        return storage.delete(descriptor.getEntityType(), descriptor.getIdentifierConverter().encode(id), version);
    }
}
