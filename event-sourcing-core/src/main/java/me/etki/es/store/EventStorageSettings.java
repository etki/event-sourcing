package me.etki.es.store;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EventStorageSettings {

    private boolean automaticRepair = true;

    public boolean getAutomaticRepair() {
        return automaticRepair;
    }

    public EventStorageSettings setAutomaticRepair(boolean automaticRepair) {
        this.automaticRepair = automaticRepair;
        return this;
    }
}
