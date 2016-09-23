package me.etki.es;

import java.time.Instant;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class Event<E, ID> {

    private Class<E> entityClass;
    private ID entityId;
    private long index;
    private Transition<E, ID> transition;
    private Instant occurredAt;
    private Instant acknowledgedAt;

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public Event<E, ID> setEntityClass(Class<E> entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    public ID getEntityId() {
        return entityId;
    }

    public Event<E, ID> setEntityId(ID entityId) {
        this.entityId = entityId;
        return this;
    }

    public long getIndex() {
        return index;
    }

    public Event<E, ID> setIndex(long index) {
        this.index = index;
        return this;
    }

    public Transition<E, ID> getTransition() {
        return transition;
    }

    public Event<E, ID> setTransition(Transition<E, ID> transition) {
        this.transition = transition;
        return this;
    }

    public Instant getOccurredAt() {
        return occurredAt;
    }

    public Event<E, ID> setOccurredAt(Instant occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public Instant getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public Event<E, ID> setAcknowledgedAt(Instant acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
        return this;
    }

    public TransitionContext<ID> produceEventContext() {
        return new TransitionContext<ID>()
                .setEntityId(entityId)
                .setIndex(index)
                .setAcknowledgedAt(acknowledgedAt)
                .setOccurredAt(occurredAt);
    }

    public E apply(E entity) {
        return transition.apply(entity, produceEventContext());
    }
}
