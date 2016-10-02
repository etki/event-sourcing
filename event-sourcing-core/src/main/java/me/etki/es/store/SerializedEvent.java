package me.etki.es.store;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class SerializedEvent {

    private String entityType;
    private String entityId;
    private long index;
    private String transitionType;
    private int transitionVersion;
    private String serializedTransition;
    private ZonedDateTime occurredAt;
    private ZonedDateTime acknowledgedAt;

    public String getEntityType() {
        return entityType;
    }

    public SerializedEvent setEntityType(String entityType) {
        this.entityType = entityType;
        return this;
    }

    public String getEntityId() {
        return entityId;
    }

    public SerializedEvent setEntityId(String entityId) {
        this.entityId = entityId;
        return this;
    }

    public long getIndex() {
        return index;
    }

    public SerializedEvent setIndex(long index) {
        this.index = index;
        return this;
    }

    public String getTransitionType() {
        return transitionType;
    }

    public SerializedEvent setTransitionType(String transitionType) {
        this.transitionType = transitionType;
        return this;
    }

    public int getTransitionVersion() {
        return transitionVersion;
    }

    public SerializedEvent setTransitionVersion(int transitionVersion) {
        this.transitionVersion = transitionVersion;
        return this;
    }

    public String getSerializedTransition() {
        return serializedTransition;
    }

    public SerializedEvent setSerializedTransition(String serializedTransition) {
        this.serializedTransition = serializedTransition;
        return this;
    }

    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }

    public SerializedEvent setOccurredAt(ZonedDateTime occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public ZonedDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public SerializedEvent setAcknowledgedAt(ZonedDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
        return this;
    }
}
