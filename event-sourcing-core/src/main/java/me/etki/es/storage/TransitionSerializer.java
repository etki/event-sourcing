package me.etki.es.storage;

import me.etki.es.EntityDescriptor;
import me.etki.es.EntityRegistry;
import me.etki.es.Event;
import me.etki.es.Transition;
import me.etki.es.EventDescriptor;
import me.etki.es.concurrent.CompletableFutures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
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
        EntityDescriptor<E, ID> registration = registry.getDescriptor(event.getEntityClass());
        @SuppressWarnings("unchecked") // safe by design
        Class<Transition<E, ID>> eventClass = (Class<Transition<E, ID>>) event.getTransition().getClass();
        EventDescriptor<E, ID> eventDescriptor = registration.getEventDescriptor(eventClass);
        return new SerializedEvent()
                .setEntityType(registration.getEntityType())
                .setEntityId(registration.getIdentifierConverter().encode(event.getEntityId()))
                .setIndex(event.getIndex())
                .setTransitionType(eventDescriptor.getEventType())
                .setTransitionVersion(eventDescriptor.getEventVersion())
                .setSerializedTransition(serializer.serialize(event.getTransition()))
                .setOccurredAt(event.getOccurredAt())
                .setAcknowledgedAt(event.getAcknowledgedAt());
    }

    private <E, ID> Event<E, ID> deserializeInternal(SerializedEvent event) throws IOException {
        EntityDescriptor<E, ID> registration = registry.getDescriptor(event.getEntityType());
        EventDescriptor<E, ID> eventDescriptor
                = registration.getEventDescriptor(event.getTransitionType(), event.getTransitionVersion());
        return new Event<E, ID>()
                .setEntityClass(registration.getEntityClass())
                .setEntityId(registration.getIdentifierConverter().decode(event.getEntityId()))
                .setIndex(event.getIndex())
                .setTransition(serializer.deserialize(event.getSerializedTransition(), eventDescriptor.getEventClass()))
                .setOccurredAt(event.getOccurredAt())
                .setAcknowledgedAt(event.getAcknowledgedAt());
    }
}
