package me.etki.es.discovery;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventDefinition {

    /**
     * Unique event type identifier; used to save and restore event, so instead of {@code long.package.path.EventName}
     * user may use just `eventName`, decoupling implementation from database internals and using shorter names.
     * @return event name
     */
    String value();

    /**
     * Stores event version. It is possible that event structure must be altered (e.g. name change), so user may
     * implement Event of next version having two versions of event structure simultaneously, while maintaining backward
     * compatibility.
     *
     * See {@link me.etki.es.DeprecatedTransition} for read repair mechanism implementation.
     *
     * @return Event version
     */
    int version() default 1;

    /**
     * @return Class of entity this event has been made for; in case of ReflectionEntityResolution engine attempts to
     * dig it out of class signature
     */
    Class<?> entity() default ReflectionEntityResolution.class;

    final class ReflectionEntityResolution {}
}
