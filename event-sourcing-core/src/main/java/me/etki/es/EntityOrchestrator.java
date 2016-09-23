package me.etki.es;

import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface EntityOrchestrator {

    <E, ID> CompletableFuture<Boolean> append(Class<E> type, ID id, Transition<E, ID> transition);
    <E, ID> Entity<E, ID> get(Class<E> type, ID id);
    <E, ID> CompletableFuture<Boolean> replace(Class<E> type, ID id, long index, Transition<E, ID> transition);
    <E, ID> CompletableFuture<Void> snapshot(Class<E> type, ID id, long index);
    <E, ID> CompletableFuture<Void> eraseSnapshot(Class<E> type, ID id, long index);
    <E, ID> void addListener(Class<E> type, EntityListener<E, ID> listener);
    <E, ID> void removeListener(Class<E> type, EntityListener<E, ID> listener);
    <E, ID> void addListener(WildcardListener listener);
    <E, ID> void removeListener(WildcardListener listener);
}
