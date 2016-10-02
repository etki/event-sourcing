package me.etki.es.container;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EntityDirectorSettings {

    private int snapshotThreshold = 25;
    private boolean awaitListeners = true;
    private boolean propagateListenerExceptions = true;
    private boolean awaitSnapshotGeneration = true;

    public int getSnapshotThreshold() {
        return snapshotThreshold;
    }

    public EntityDirectorSettings setSnapshotThreshold(int snapshotThreshold) {
        this.snapshotThreshold = snapshotThreshold;
        return this;
    }

    public boolean shouldAwaitListeners() {
        return awaitListeners;
    }

    public EntityDirectorSettings setAwaitListeners(boolean awaitListeners) {
        this.awaitListeners = awaitListeners;
        return this;
    }

    public boolean shouldPropagateListenerExceptions() {
        return propagateListenerExceptions;
    }

    public EntityDirectorSettings setPropagateListenerExceptions(boolean propagateListenerExceptions) {
        this.propagateListenerExceptions = propagateListenerExceptions;
        return this;
    }

    public boolean shouldAwaitSnapshotGeneration() {
        return awaitSnapshotGeneration;
    }

    public EntityDirectorSettings setAwaitSnapshotGeneration(boolean awaitSnapshotGeneration) {
        this.awaitSnapshotGeneration = awaitSnapshotGeneration;
        return this;
    }
}
