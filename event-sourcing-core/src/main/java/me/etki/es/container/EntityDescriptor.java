package me.etki.es.container;

import me.etki.es.Transition;
import me.etki.es.store.IdentifierConverter;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface EntityDescriptor<E, ID> {

    EntityType<E> getEntityType();
    Class<ID> getIdClass();

    IdentifierConverter<ID> getIdentifierConverter();

    TransitionTypeDescriptor<E, ID> getTransitionType(Class<? extends Transition<E, ID>> transitionClass);
    TransitionTypeDescriptor<E, ID> getTransitionType(String transitionType, int transitionVersion);
}
