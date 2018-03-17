package me.etki.es.store;

import me.etki.es.Transition;
import me.etki.es.concurrent.CompletableFutures;
import me.etki.es.container.EntityDescriptor;
import me.etki.es.container.EntityId;
import me.etki.es.container.Event;
import me.etki.es.container.TransitionTypeDescriptor;
import me.etki.es.engine.EntityRegistry;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class TransitionSerializer {

    private final Serializer serializer;
    private final EntityRegistry registry;

    public TransitionSerializer(Serializer serializer, EntityRegistry registry) {
        this.serializer = serializer;
        this.registry = registry;
    }

    public <E, ID> CompletableFuture<SerializedEvent> serialize(Event<E, ID> event) {
        return CompletableFutures.wrapExecution(() -> serializeInternal(event));
    }

    public <E, ID> CompletableFuture<Event<E, ID>> deserialize(SerializedEvent event) {
        return CompletableFutures.wrapExecution(() -> deserializeInternal(event));
    }

    public <E, ID> CompletableFuture<List<SerializedEvent>> serializeAll(Iterable<Event<E, ID>> events) {
        return CompletableFutures
                .wrapExecution(() -> {
                    List<SerializedEvent> result = new ArrayList<>();
                    for (Event<E, ID> event : events) {
                        result.add(serializeInternal(event));
                    }
                    return result;
                });
    }

    public <E, ID> CompletableFuture<List<Event<E, ID>>> deserializeAll(Iterable<SerializedEvent> events) {
        return CompletableFutures
                .wrapExecution(() -> {
                    List<Event<E, ID>> result = new ArrayList<>();
                    for (SerializedEvent event : events) {
                        result.add(deserializeInternal(event));
                    }
                    return result;
                });
    }

    private <E, ID> SerializedEvent serializeInternal(Event<E, ID> event) throws IOException {
        return new SerializedEvent()
                .setEntityType(event.getEntityType().getEntityType())
                .setEntityId(event.getEntityId().getEncodedId())
                .setIndex(event.getIndex())
                .setTransitionType(event.getTransitionType().getTransitionType())
                .setTransitionVersion(event.getTransitionType().getTransitionTypeVersion())
                .setSerializedTransition(serializer.serialize(event.getTransition()))
                .setOccurredAt(event.getOccurredAt())
                .setAcknowledgedAt(event.getAcknowledgedAt());
    }

    private <E, ID> Event<E, ID> deserializeInternal(SerializedEvent event) throws IOException {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(event.getEntityType());
        TransitionTypeDescriptor<E, ID> transitionType = descriptor
                .getTransitionType(event.getTransitionType(), event.getTransitionVersion());
        Transition<E, ID> transition = serializer
                .deserialize(event.getSerializedTransition(), transitionType.getTransitionClass());
        ID entityId = descriptor.getIdentifierConverter().decode(event.getEntityId());
        return new Event<E, ID>()
                .setEntityType(descriptor.getEntityType())
                .setEntityId(new EntityId<>(entityId, event.getEntityId()))
                .setIndex(event.getIndex())
                .setTransitionType(transitionType)
                .setTransition(transition)
                .setOccurredAt(event.getOccurredAt())
                .setAcknowledgedAt(event.getAcknowledgedAt());
    }
}
