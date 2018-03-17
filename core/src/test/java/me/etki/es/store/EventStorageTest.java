package me.etki.es.store;

import me.etki.es.engine.EntityRegistry;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventStorageTest {

    private TransitionSerializer serializer;
    private EntityRegistry registry;
    private EventStorageSettings settings;

    @Before
    public void before() {
        serializer = Mockito.mock(TransitionSerializer.class);
        registry = new EntityRegistry();
        settings = new EventStorageSettings();
    }

    @Test
    public void throwsIfNonBrowseableStoreIsBrowsed() {
        SerializedEventStore store = Mockito.mock(SerializedEventStore.class);
        EventStorage storage = new EventStorage(store, serializer, registry, settings);
        Assertions.assertThrows(UnsupportedOperationException.class, () -> storage.getStoredEntities(0, 1));
    }
}
