package me.etki.es;

/**
 * An interface for transition with obsolete schema.
 *
 * This interface allows transition class to declare itself deprecated and provide modern representation of itself.
 * This is designed for:
 * <ul>
 *     <li>Case when transition schema has to be updated, but it is not possible to maintain backward compatibility</li>
 *     <li>Case when transition becomes obsolete and it's definition is substituted by another transition (e.g.
 *     {@code fileAttached} transition may be absorbed by {@code filesAttached} transition, which expects URI
 *     collection to be passed in constructor, but not just single unwrapped URI)
 *     </li>
 * </ul>
 * In those cases developer may update now-obsolete transition with current interface and provide method which will
 * return new transition when called, so mentioned {@code fileAttached} transition may return {@code filesAttached}
 * transition with single-item collection. This allows engine to perform read repairs, which, if applied on all
 * entities, will let end developer to get rid of obsolete transition.
 *
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface DeprecatedTransition<E, ID> extends Transition<E, ID> {

    Transition<E, ID> upgrade();
}
