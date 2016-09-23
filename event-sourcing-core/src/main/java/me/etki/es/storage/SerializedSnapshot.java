package me.etki.es.storage;

import java.time.Instant;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class SerializedSnapshot {

    private String entityType;
    private String entityId;
    private long entityVersion;
    private String serializedEntity;
    private Instant transitionedAt;
    private Instant transitionAcknowledgedAt;
    private Instant takenAt;

    public String getEntityType() {
        return entityType;
    }

    public SerializedSnapshot setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public SerializedSnapshot setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public long getEntityVersion() {
        return entityVersion;
    }

    public SerializedSnapshot setEntityVersion(long entityVersion) {
        this.entityVersion = entityVersion;
        return this;
    }

    public String getSerializedEntity() {
        return serializedEntity;
    }

    public SerializedSnapshot setSerializedEntity(String serializedEntity) {
        this.serializedEntity = serializedEntity;
        return this;
    }

    public Instant getTransitionedAt() {
        return transitionedAt;
    }

    public SerializedSnapshot setTransitionedAt(Instant transitionedAt) {
        this.transitionedAt = transitionedAt;
        return this;
    }

    public Instant getTransitionAcknowledgedAt() {
        return transitionAcknowledgedAt;
    }

    public SerializedSnapshot setTransitionAcknowledgedAt(Instant transitionAcknowledgedAt) {
        this.transitionAcknowledgedAt = transitionAcknowledgedAt;
        return this;
    }

    public Instant getTakenAt() {
        return takenAt;
    }

    public SerializedSnapshot setTakenAt(Instant takenAt) {
        this.takenAt = takenAt;
        return this;
    }
}
