package me.etki.es;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface EntityDirector<E, ID> {

    default CompletableFuture<Entity<E, ID>> get(ID id) {
        return getAt(id, Long.MAX_VALUE);
    }
    CompletableFuture<Entity<E, ID>> getAt(ID id, long index);
    CompletableFuture<Event<E, ID>> apply(ID id, Transition<E, ID> transition, Instant timestamp);
    CompletableFuture<List<Event<E, ID>>> getEvents(ID id, long skip, long size);

    CompletableFuture<Entity<E, ID>> snapshot(ID id, long index);
    CompletableFuture<Void> dropSnapshot(ID id, long index);
    CompletableFuture<List<Snapshot<E, ID>>> getSnapshots(ID id, long skip, long size);
}
