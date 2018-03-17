package me.etki.es.store;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface SerializedEventStore {

    /**
     * Returns number of events in storage. Please note that this is (usually) different from event count: in case of
     * storage failure some records may be lost and this method may legally return value lower than
     * {@link #getMaximumEventNumber}.
     *
     * @param entityType Entity type.
     * @param entityId Encoded entity id.
     *
     * @return CompletableFuture-wrapped number of events in storage.
     */
    CompletableFuture<Long> count(String entityType, String entityId);

    /**
     * Returns max event number present in storage for selected entity.
     *
     * @param entityType Entity type.
     * @param entityId Encoded entity id
     *
     * @return CompletableFuture-wrapped maximum event number.
     */
    CompletableFuture<Long> getMaximumEventNumber(String entityType, String entityId);

    /**
     * Simply returns slice of serialized events in ascending order.
     *
     * @param entityType Entity type.
     * @param entityId Encoded entity id.
     * @param skip Number of records to skip.
     * @param size Number of records to fetch.
     * @return CompletableFuture-wrapped list of events.
     */
    CompletableFuture<List<SerializedEvent>> getSlice(String entityType, String entityId, long skip, long size);

    /**
     * Saves new event (if event index is not occupied).
     *
     * @param event Event to save.
     *
     * @return Wrapped operation result: true if event has been inserted, false if event index has been occupied by the
     * moment of insertion.
     */
    CompletableFuture<Boolean> insert(SerializedEvent event);
}
