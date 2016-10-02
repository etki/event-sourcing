package me.etki.es.container;

import me.etki.es.Transition;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class TransitionTypeDescriptor<E, ID> {

    private final Class<Transition<E, ID>> transitionClass;
    private final String transitionType;
    private final int transitionTypeVersion;

    public TransitionTypeDescriptor(
            Class<Transition<E, ID>> transitionClass,
            String transitionType,
            int transitionTypeVersion) {

        this.transitionClass = transitionClass;
        this.transitionType = transitionType;
        this.transitionTypeVersion = transitionTypeVersion;
    }

    public Class<Transition<E, ID>> getTransitionClass() {
        return transitionClass;
    }

    public String getTransitionType() {
        return transitionType;
    }

    public int getTransitionTypeVersion() {
        return transitionTypeVersion;
    }
}
