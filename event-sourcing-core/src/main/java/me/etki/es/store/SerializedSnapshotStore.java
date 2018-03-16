package me.etki.es.store;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface SerializedSnapshotStore {

    CompletableFuture<Long> count(String type, String id);
    CompletableFuture<List<SerializedSnapshot>> getSlice(String type, String id, long skip, long size);
    CompletableFuture<SerializedSnapshot> get(String type, String id, long version);
    CompletableFuture<SerializedSnapshot> getLast(String type, String id);
    CompletableFuture<SerializedSnapshot> getLastUpTo(String type, String id, long version);
    CompletableFuture<Void> save(SerializedSnapshot snapshot);
}
