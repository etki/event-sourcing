package me.etki.es.store;

import me.etki.es.container.EntityDescriptor;
import me.etki.es.container.EntityId;
import me.etki.es.engine.EntityRegistry;
import me.etki.es.container.Snapshot;
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
public class SnapshotSerializer {

    private final Serializer serializer;
    private final EntityRegistry registry;

    public SnapshotSerializer(Serializer serializer, EntityRegistry registry) {
        this.serializer = serializer;
        this.registry = registry;
    }

    public <E, ID> CompletableFuture<SerializedSnapshot> serialize(Snapshot<E, ID> snapshot) {
        return CompletableFutures.wrapExecution(() -> serializeInternal(snapshot));
    }

    public <E, ID> CompletableFuture<Snapshot<E, ID>> deserialize(SerializedSnapshot snapshot) {
        return CompletableFutures.wrapExecution(() -> deserializeInternal(snapshot));
    }

    public <E, ID> CompletableFuture<List<SerializedSnapshot>> serializeAll(Iterable<Snapshot<E, ID>> snapshots) {
        return CompletableFutures
                .wrapExecution(() -> {
                    List<SerializedSnapshot> result = new ArrayList<>();
                    for (Snapshot<E, ID> snapshot : snapshots) {
                        result.add(serializeInternal(snapshot));
                    }
                    return result;
                });
    }

    public <E, ID> CompletableFuture<List<Snapshot<E, ID>>> deserializeAll(Iterable<SerializedSnapshot> snapshots) {
        return CompletableFutures
                .wrapExecution(() -> {
                    List<Snapshot<E, ID>> result = new ArrayList<>();
                    for (SerializedSnapshot snapshot : snapshots) {
                        result.add(deserializeInternal(snapshot));
                    }
                    return result;
                });
    }

    private <E, ID> SerializedSnapshot serializeInternal(Snapshot<E, ID> snapshot) throws IOException {
        return new SerializedSnapshot()
                .setEntityType(snapshot.getEntityType().getEntityType())
                .setEntityId(snapshot.getEntityId().getEncodedId())
                .setEntityVersion(snapshot.getEntityVersion())
                .setSerializedEntity(serializer.serialize(snapshot.getEntity()))
                .setTransitionedAt(snapshot.getTransitionedAt())
                .setTransitionAcknowledgedAt(snapshot.getTransitionAcknowledgedAt())
                .setTakenAt(snapshot.getTakenAt());
    }

    private <E, ID> Snapshot<E, ID> deserializeInternal(SerializedSnapshot snapshot) throws IOException {
        EntityDescriptor<E, ID> descriptor = registry.getDescriptor(snapshot.getEntityType());
        E entity = serializer.deserialize(snapshot.getSerializedEntity(), descriptor.getEntityType().getEntityClass());
        ID id = descriptor.getIdentifierConverter().decode(snapshot.getEntityId());
        return new Snapshot<E, ID>()
                .setEntityType(descriptor.getEntityType())
                .setEntityId(new EntityId<>(id, snapshot.getEntityId()))
                .setEntity(entity)
                .setEntityVersion(snapshot.getEntityVersion())
                .setTransitionedAt(snapshot.getTransitionedAt())
                .setTransitionAcknowledgedAt(snapshot.getTransitionAcknowledgedAt())
                .setTakenAt(snapshot.getTakenAt());
    }
}
