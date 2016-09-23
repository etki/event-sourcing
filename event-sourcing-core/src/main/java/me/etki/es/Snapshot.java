package me.etki.es;

import java.time.Instant;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class Snapshot<E, ID> {

    private Class<E> entityClass;
    private ID entityId;
    private E entity;
    private long entityVersion;
    private Instant transitionedAt;
    private Instant transitionAcknowledgedAt;
    private Instant takenAt;

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public Snapshot<E, ID> setEntityClass(Class<E> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public ID getEntityId() {
        return entityId;
    }

    public Snapshot<E, ID> setEntityId(ID entityId) {
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

    public Instant getTransitionedAt() {
        return transitionedAt;
    }

    public Snapshot<E, ID> setTransitionedAt(Instant transitionedAt) {
        this.transitionedAt = transitionedAt;
        return this;
    }

    public Instant getTransitionAcknowledgedAt() {
        return transitionAcknowledgedAt;
    }

    public Snapshot<E, ID> setTransitionAcknowledgedAt(Instant transitionAcknowledgedAt) {
        this.transitionAcknowledgedAt = transitionAcknowledgedAt;
        return this;
    }

    public Instant getTakenAt() {
        return takenAt;
    }

    public Snapshot<E, ID> setTakenAt(Instant takenAt) {
        this.takenAt = takenAt;
        return this;
    }
}
