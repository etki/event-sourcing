package me.etki.es.container;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class Entity<E, ID> {

    private EntityId<ID> id;
    private EntityType<E> type;
    private E entity;
    private Long version;
    private Long snapshotVersion;
    private ZonedDateTime transitionedAt;
    private ZonedDateTime transitionAcknowledgedAt;
    private ZonedDateTime snapshotTakenAt;

    public EntityId<ID> getId() {
        return id;
    }

    public Entity<E, ID> setId(EntityId<ID> id) {
        this.id = id;
        return this;
    }

    public EntityType<E> getType() {
        return type;
    }

    public Entity<E, ID> setType(EntityType<E> type) {
        this.type = type;
        return this;
    }

    public E getEntity() {
        return entity;
    }

    public Entity<E, ID> setEntity(E entity) {
        this.entity = entity;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Entity<E, ID> setVersion(Long version) {
        this.version = version;
        return this;
    }

    public Long getSnapshotVersion() {
        return snapshotVersion;
    }

    public Entity<E, ID> setSnapshotVersion(Long snapshotVersion) {
        this.snapshotVersion = snapshotVersion;
        return this;
    }

    public ZonedDateTime getTransitionedAt() {
        return transitionedAt;
    }

    public Entity<E, ID> setTransitionedAt(ZonedDateTime transitionedAt) {
        this.transitionedAt = transitionedAt;
        return this;
    }

    public ZonedDateTime getTransitionAcknowledgedAt() {
        return transitionAcknowledgedAt;
    }

    public Entity<E, ID> setTransitionAcknowledgedAt(ZonedDateTime transitionAcknowledgedAt) {
        this.transitionAcknowledgedAt = transitionAcknowledgedAt;
        return this;
    }

    public ZonedDateTime getSnapshotTakenAt() {
        return snapshotTakenAt;
    }

    public Entity<E, ID> setSnapshotTakenAt(ZonedDateTime snapshotTakenAt) {
        this.snapshotTakenAt = snapshotTakenAt;
        return this;
    }
}
