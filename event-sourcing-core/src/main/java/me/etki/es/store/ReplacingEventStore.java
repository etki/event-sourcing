package me.etki.es.store;

import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface ReplacingEventStore extends SerializedEventStore {

    /**
     * Replaces event if it exists. This functionality is required for read repair ability only.
     *
     * @param event Event to save.
     *
     * @return Wrapped operation result: true if event has been existing and has been successfully replaced, false if
     * event hasn't been existing so no replacement could be made.
     */
    CompletableFuture<Boolean> replace(SerializedEvent event);
}
