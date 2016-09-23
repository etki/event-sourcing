package me.etki.es;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface Transition<E, ID> {

    E apply(E entity, TransitionContext<ID> context);
}
