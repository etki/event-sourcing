package me.etki.es.store;

import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface PurgingSnapshotStore extends SerializedSnapshotStore {

    /**
     * Ensures particular snapshot doesn't exist.
     *
     * @param entityType Entity type.
     * @param entityId Entity id.
     * @param version Snapshotted version.
     *
     * @return Promise.
     */
    CompletableFuture<Void> delete(String entityType, String entityId, long version);

    /**
     * Wipes out all snapshots for entity.
     *
     * @param entityType Entity type
     * @param entityId Entity id.
     *
     * @return Promise.
     */
    CompletableFuture<Void> purge(String entityType, String entityId);
}
