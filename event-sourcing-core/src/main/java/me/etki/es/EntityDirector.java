package me.etki.es;

import me.etki.es.exception.UnregisteredEntityException;
import me.etki.es.exception.UnregisteredTransitionException;
import me.etki.es.storage.StoredEntity;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface EntityDirector {

    /**
     * Appends new transition to entity history.
     *
     * @param type Entity class.
     * @param id Entity identifier.
     * @param transition Transition to be appended to event stream.
     * @param timestamp Time at which transition has took place.
     * @param <E> Entity class.
     * @param <ID> Entity identifier class.
     * @return CompletableFuture-wrapped success status: true if all went ok, false in case of optimistic locking
     * failure, wrapped exception in case things went south.
     * @throws UnregisteredTransitionException Thrown in case transition hasn't been registered in engine itself.
     * @throws UnregisteredEntityException Thrown in case entity hasn't been registered in engine itself.
     */
    <E, ID> CompletableFuture<Boolean> append(Class<E> type, ID id, Transition<E, ID> transition, Instant timestamp)
            throws UnregisteredEntityException, UnregisteredTransitionException;

    <E, ID> CompletableFuture<Entity<E, ID>> get(Class<E> type, ID id) throws UnregisteredEntityException;
    <E, ID> EventStream<E, ID> getEventStream(Class<E> type, ID id) throws UnregisteredEntityException;

    <E, ID> CompletableFuture<Void> purge(Class<E> type, ID id) throws UnregisteredEntityException;

    <E, ID> CompletableFuture<Void> snapshot(Class<E> type, ID id, long index) throws UnregisteredEntityException;
    <E, ID> CompletableFuture<Void> eraseSnapshot(Class<E> type, ID id, long index) throws UnregisteredEntityException;

    CompletableFuture<List<StoredEntity>> getStoredEntities(long skip, long size) throws UnsupportedOperationException;

    <E, ID> void addEntityListener(Class<E> type, EntityListener<E, ID> listener);
    <E, ID> void removeEntityListener(Class<E> type, EntityListener<E, ID> listener);

    void addEntityListener(WildcardListener listener);
    void removeEntityListener(WildcardListener listener);
}
