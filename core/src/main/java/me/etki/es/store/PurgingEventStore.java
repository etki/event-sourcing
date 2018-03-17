package me.etki.es.store;

import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface PurgingEventStore extends SerializedEventStore {

    /**
     * Drops all events for specified events.
     *
     * Even though event sourcing is often described as immutable, you need to free up disk space from time to time.
     *
     * @param entityType Entity type.
     * @param entityId Entity id.
     *
     * @return Promise.
     */
    CompletableFuture<Void> purge(String entityType, String entityId);
}
