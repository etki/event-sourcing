package me.etki.es.container;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class TransitionContext<E, ID> {

    private EntityId<ID> entityId;
    private EntityType<E> entityType;
    private TransitionTypeDescriptor<E, ID> transitionType;
    private long index;
    private ZonedDateTime occurredAt;
    private ZonedDateTime acknowledgedAt;

    public EntityId<ID> getEntityId() {
        return entityId;
    }

    public TransitionContext<E, ID>  setEntityId(EntityId<ID> entityId) {
        this.entityId = entityId;
        return this;
    }

    public EntityType<E> getEntityType() {
        return entityType;
    }

    public TransitionContext<E, ID>  setEntityType(EntityType<E> entityType) {
        this.entityType = entityType;
        return this;
    }

    public TransitionTypeDescriptor<E, ID> getTransitionType() {
        return transitionType;
    }

    public TransitionContext<E, ID>  setTransitionType(TransitionTypeDescriptor<E, ID> transitionType) {
        this.transitionType = transitionType;
        return this;
    }

    public long getIndex() {
        return index;
    }

    public TransitionContext<E, ID>  setIndex(long index) {
        this.index = index;
        return this;
    }

    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }

    public TransitionContext<E, ID>  setOccurredAt(ZonedDateTime occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public ZonedDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public TransitionContext<E, ID>  setAcknowledgedAt(ZonedDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
        return this;
    }
}
