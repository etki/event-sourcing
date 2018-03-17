package me.etki.es.container;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@Accessors(chain = true)
public class EntityDirectorSettings {

    @Getter
    @Setter
    private int snapshotThreshold = 25;
    @Setter
    private boolean awaitListeners = true;
    @Setter
    private boolean propagateListenerExceptions = true;
    @Setter
    private boolean awaitSnapshotGeneration = true;

    public boolean shouldAwaitSnapshotGeneration() {
        return this.awaitSnapshotGeneration;
    }

    public boolean shouldAwaitListeners() {
        return awaitListeners;
    }

    public boolean shouldPropagateListenerExceptions() {
        return this.propagateListenerExceptions;
    }
}
