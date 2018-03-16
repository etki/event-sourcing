package me.etki.es.container;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.etki.es.Transition;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class TransitionTypeDescriptor<E, ID> {

    @Getter
    private final Class<Transition<E, ID>> transitionClass;
    @Getter
    private final String transitionType;
    @Getter
    private final int transitionTypeVersion;
}
