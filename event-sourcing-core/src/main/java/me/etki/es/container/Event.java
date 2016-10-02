package me.etki.es.container;

import me.etki.es.Transition;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class Event<E, ID> {

    private EntityType<E> entityType;
    private EntityId<ID> entityId;
    private long index;
    private TransitionTypeDescriptor<E, ID> transitionType;
    private Transition<E, ID> transition;
    private ZonedDateTime occurredAt;
    private ZonedDateTime acknowledgedAt;

    public EntityType<E> getEntityType() {
        return entityType;
    }

    public Event<E, ID> setEntityType(EntityType<E> entityType) {
        this.entityType = entityType;
        return this;
    }

    public EntityId<ID> getEntityId() {
        return entityId;
    }

    public Event<E, ID> setEntityId(EntityId<ID> entityId) {
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

    public TransitionTypeDescriptor<E, ID> getTransitionType() {
        return transitionType;
    }

    public Event<E, ID> setTransitionType(TransitionTypeDescriptor<E, ID> transitionType) {
        this.transitionType = transitionType;
        return this;
    }

    public Transition<E, ID> getTransition() {
        return transition;
    }

    public Event<E, ID> setTransition(Transition<E, ID> transition) {
        this.transition = transition;
        return this;
    }

    public ZonedDateTime getOccurredAt() {
        return occurredAt;
    }

    public Event<E, ID> setOccurredAt(ZonedDateTime occurredAt) {
        this.occurredAt = occurredAt;
        return this;
    }

    public ZonedDateTime getAcknowledgedAt() {
        return acknowledgedAt;
    }

    public Event<E, ID> setAcknowledgedAt(ZonedDateTime acknowledgedAt) {
        this.acknowledgedAt = acknowledgedAt;
        return this;
    }

    public TransitionContext<E, ID> toTransitionContext() {
        return new TransitionContext<E, ID>()
                .setEntityId(entityId)
                .setEntityType(entityType)
                .setTransitionType(transitionType)
                .setIndex(index)
                .setAcknowledgedAt(acknowledgedAt)
                .setOccurredAt(occurredAt);
    }

    public E apply(E entity) {
        return transition.apply(entity, toTransitionContext());
    }
}
