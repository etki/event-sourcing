package me.etki.es;

import me.etki.es.container.EntityDescriptor;
import me.etki.es.container.Event;
import me.etki.es.container.TransitionContext;

/**
 * Single entity state mutation.
 *
 * Implementation of this interface has to
 *
 * <ul>
 *     <li>Accept entity and return same or another instance of entity with implied transition applied to entity state,
 *     which is determined by previous transitions whose order is defined only by library-consuming code - that means if
 *     one of your transitions turns entity into null, the next one should be ready to work with null. Also be aware
 *     that first transition is stream is always fed with null instead of entity.
 *     </li>
 *     <li>Provide necessary information for serializer (e.g. @JsonCreator and getters for jackson-based serializer)
 *     </li>
 *     <li>Perform safe transformation and refrain from any validation and throwing exceptions - this has to be done
 *     from transition-producing code, not inside transition. Transitions <b>must</b> ensure as much safety as
 *     possible and try to walk around edge cases, e.g. if your transition modifies collection property, be ready
 *     that previous events somehow failed to initialize it (or snapshot serialization went south) and there may be null
 *     instead of empty collection</li>
 * </ul>
 *
 * @see DeprecatedTransition
 * @see Event
 * @see EntityDescriptor
 *
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface Transition<E, ID> {

    E apply(E entity, TransitionContext<E, ID> context);
}
