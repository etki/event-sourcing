package me.etki.es.container;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class Snapshot<E, ID> {

    private EntityType<E> entityType;
    private EntityId<ID> entityId;
    private E entity;
    private long entityVersion = 0;
    private ZonedDateTime transitionedAt;
    private ZonedDateTime transitionAcknowledgedAt;
    private ZonedDateTime takenAt;

    public EntityType<E> getEntityType() {
        return entityType;
    }

    public Snapshot<E, ID> setEntityType(EntityType<E> entityType) {
        this.entityType = entityType;
        return this;
    }

    public EntityId<ID> getEntityId() {
        return entityId;
    }

    public Snapshot<E, ID> setEntityId(EntityId<ID> entityId) {
        this.entityId = entityId;
        return this;
    }

    public E getEntity() {
        return entity;
    }

    public Snapshot<E, ID> setEntity(E entity) {
        this.entity = entity;
        return this;
    }

    public long getEntityVersion() {
        return entityVersion;
    }

    public Snapshot<E, ID> setEntityVersion(long entityVersion) {
        this.entityVersion = entityVersion;
        return this;
    }

    public ZonedDateTime getTransitionedAt() {
        return transitionedAt;
    }

    public Snapshot<E, ID> setTransitionedAt(ZonedDateTime transitionedAt) {
        this.transitionedAt = transitionedAt;
        return this;
    }

    public ZonedDateTime getTransitionAcknowledgedAt() {
        return transitionAcknowledgedAt;
    }

    public Snapshot<E, ID> setTransitionAcknowledgedAt(ZonedDateTime transitionAcknowledgedAt) {
        this.transitionAcknowledgedAt = transitionAcknowledgedAt;
        return this;
    }

    public ZonedDateTime getTakenAt() {
        return takenAt;
    }

    public Snapshot<E, ID> setTakenAt(ZonedDateTime takenAt) {
        this.takenAt = takenAt;
        return this;
    }
}
