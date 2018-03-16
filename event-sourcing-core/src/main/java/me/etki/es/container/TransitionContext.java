package me.etki.es.container;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@Accessors(chain = true)
@EqualsAndHashCode(of = {"entityType", "entityId", "index"})
public class TransitionContext<E, ID> {

    @Getter
    @Setter
    private EntityId<ID> entityId;
    @Getter
    @Setter
    private EntityType<E> entityType;
    @Getter
    @Setter
    private TransitionTypeDescriptor<E, ID> transitionType;
    @Getter
    @Setter
    private long index;
    @Getter
    @Setter
    private ZonedDateTime occurredAt;
    @Getter
    @Setter
    private ZonedDateTime acknowledgedAt;
}
