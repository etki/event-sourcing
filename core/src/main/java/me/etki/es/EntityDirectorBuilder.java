package me.etki.es;

import me.etki.es.container.EntityDirectorSettings;
import me.etki.es.engine.EntityRegistry;
import me.etki.es.store.EventStorageSettings;
import me.etki.es.store.SerializedEventStore;
import me.etki.es.store.SerializedSnapshotStore;
import me.etki.es.store.Serializer;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EntityDirectorBuilder {

    private EntityRegistry registry = new EntityRegistry();
    private EntityDirectorSettings entityDirectorSettings = new EntityDirectorSettings();
    private EventStorageSettings eventStorageSettings = new EventStorageSettings();
    private Serializer serializer;
    private SerializedEventStore eventStore;
    private SerializedSnapshotStore snapshotStore;

    public EntityDirectorBuilder reset() {
        registry = new EntityRegistry();
        entityDirectorSettings = new EntityDirectorSettings();
        eventStorageSettings = new EventStorageSettings();
        serializer = null;
        eventStore = null;
        snapshotStore = null;
        return this;
    }
}
