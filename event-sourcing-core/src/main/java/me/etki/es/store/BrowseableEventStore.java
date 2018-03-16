package me.etki.es.store;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface BrowseableEventStore {

    /**
     * Retrieves description of stored entities.
     *
     * @param skip Number of entities to skip,
     * @param size Number of entities to return.
     *
     * @return Wrapped list of stored entity descriptions.
     */
    CompletableFuture<List<StoredEntity>> getStoredEntities(long skip, long size);

    /**
     * Counts total amount of stored entities
     *
     * @return Promise with entity amount.
     */
    CompletableFuture<Long> countStoredEntities();
}
