package me.etki.es;

import java.time.Instant;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class Entity<E, ID> {

    private ID id;
    private E entity;
    private Long version;
    private Long snapshotVersion;
    private Instant modifiedAt;
    private Instant acknowledgedAt;
    private Instant snapshottedAt;

    public ID getId() {
        return id;
    }

    public Entity setId(ID id) {
        this.id = id;
        return this;
    }

    public E getEntity() {
        return entity;
    }

    public Entity setEntity(E entity) {
        this.entity = entity;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Entity setVersion(Long version) {
        this.version = version;
        return this;
    }

    public Long getSnapshotVersion() {
        return snapshotVersion;
    }

    public Entity setSnapshotVersion(Long snapshotVersion) {
        this.snapshotVersion = snapshotVersion;
        return this;
    }

    public Instant getModifiedAt() {
        return modifiedAt;
    }

    public Entity setModifiedAt(Instant modifiedAt) {
        this.modifiedAt = modifiedAt;
        return this;
    }

    public Instant getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public Entity setAcknowledgedAt(Instant acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
        return this;
    }

    public Instant getSnapshottedAt() {
        return snapshottedAt;
    }

    public Entity setSnapshottedAt(Instant snapshottedAt) {
        this.snapshottedAt = snapshottedAt;
        return this;
    }
}
