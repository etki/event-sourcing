package me.etki.es.storage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface SerializedEventStorage {

    CompletableFuture<Long> count(String type, String id);
    CompletableFuture<List<SerializedEvent>> getSlice(String type, String id, long skip, long size);

    /**
     * Saves new event (if event index is not occupied)
     *
     * @param event Event to save
     * @return Wrapped operation result: true if event has been inserted, false if event index has been occupied by the
     * moment of insertion.
     */
    CompletableFuture<Boolean> insert(SerializedEvent event);

    /**
     * Replaces event if it exists. This functionality is required for read repair ability.
     *
     * @param event Event to save
     * @return Wrapped operation result: true if event has been existing and has been successfully replaced, false if
     * event hasn't been existing so no replacement could be made.
     */
    CompletableFuture<Boolean> replace(SerializedEvent event);
}
