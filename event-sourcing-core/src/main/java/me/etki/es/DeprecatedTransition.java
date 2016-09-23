package me.etki.es;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface DeprecatedTransition<E, ID> extends Transition<E, ID> {

    Transition<E, ID> upgrade();
}
