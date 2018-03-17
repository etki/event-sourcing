package me.etki.es.container;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import me.etki.es.Transition;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@Accessors(chain = true)
@EqualsAndHashCode(of = {"entityType", "entityId", "index"})
public class Event<E, ID> {

    @Getter
    @Setter
    private EntityType<E> entityType;
    @Getter
    @Setter
    private EntityId<ID> entityId;
    @Getter
    @Setter
    private long index;
    @Getter
    @Setter
    private TransitionTypeDescriptor<E, ID> transitionType;
    @Getter
    @Setter
    private Transition<E, ID> transition;
    @Getter
    @Setter
    private ZonedDateTime occurredAt;
    @Getter
    @Setter
    private ZonedDateTime acknowledgedAt;

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
