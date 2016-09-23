package me.etki.es;

import java.time.Instant;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class TransitionContext<ID> {

    private ID entityId;
    private long index;
    private Instant occurredAt;
    private Instant acknowledgedAt;

    public ID getEntityId() {
        return entityId;
    }

    public TransitionContext<ID> setEntityId(ID entityId) {
        this.entityId = entityId;
        return this;
    }

    public long getIndex() {
        return index;
    }

    public TransitionContext<ID> setIndex(long index) {
        this.index = index;
        return this;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public TransitionContext<ID> setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public Instant getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public TransitionContext<ID> setAcknowledgedAt(Instant acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
        return this;
    }
}
