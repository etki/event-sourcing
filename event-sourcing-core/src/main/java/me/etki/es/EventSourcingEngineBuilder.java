package me.etki.es;

import me.etki.es.storage.Serializer;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EventSourcingEngineBuilder {

    private EntityRegistry registry = new EntityRegistry();
    private long snapshotThreshold = 25;
    private boolean automaticRepair = true;
    private boolean awaitListeners = true;
    private Serializer serializer;
}
