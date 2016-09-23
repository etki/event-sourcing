package me.etki.es.storage;

import me.etki.es.EntityDescriptor;
import me.etki.es.EntityRegistry;
import me.etki.es.Event;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EventStorage {

    private final SerializedEventStorage storage;
    private final TransitionSerializer serializer;
    private final EntityRegistry registry;

    public EventStorage(SerializedEventStorage storage, TransitionSerializer serializer, EntityRegistry registry) {
        this.storage = storage;
        this.serializer = serializer;
        this.registry = registry;
    }

    public <E, ID> CompletableFuture<Long> count(Class<E> type, ID id) {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(type);
        return storage.count(descriptor.getEntityType(), descriptor.getIdentifierConverter().encode(id));
    }

    public <E, ID> CompletableFuture<List<Event<E, ID>>> getSlice(Class<E> type, ID id, long skip, long size) {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(type);
        return storage
                .getSlice(descriptor.getEntityType(), descriptor.getIdentifierConverter().encode(id), skip, size)
                .thenCompose(serializer::deserializeAll);
    }

    public <E, ID> CompletableFuture<Boolean> insert(Event<E, ID> event) {
        return serializer
                .serialize(event)
                .thenCompose(storage::insert);
    }

    private <E> String getEntityType(Class<E> type) {
        return registry.getDescriptor(type).getEntityType();
    }

    private <E, ID> String serializeId(Class<E> type, ID id) {
        return registry.getDescriptor(type).getIdentifierConverter().encode(id);
    }
}
