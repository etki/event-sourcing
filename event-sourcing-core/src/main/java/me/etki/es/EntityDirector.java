package me.etki.es;

import me.etki.es.container.Entity;
import me.etki.es.exception.UnregisteredEntityException;
import me.etki.es.exception.UnregisteredTransitionException;
import me.etki.es.listener.EntityListener;
import me.etki.es.listener.WildcardListener;
import me.etki.es.store.StoredEntity;

import java.time.ZonedDateTime;
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
     * @param occurredAt Time at which transition has took place.
     * @param acknowledgedAt Time at which transition has been acknowledged by application.
     * @param <E> Entity class.
     * @param <ID> Entity identifier class.
     * @return CompletableFuture-wrapped success status: true if all went ok, false in case of optimistic locking
     * failure, wrapped exception in case things went south.
     * @throws UnregisteredTransitionException Thrown in case transition hasn't been registered in engine itself.
     * @throws UnregisteredEntityException Thrown in case entity hasn't been registered in engine itself.
     */
    <E, ID> CompletableFuture<Boolean> append(
            Class<E> type,
            ID id,
            Transition<E, ID> transition,
            ZonedDateTime occurredAt,
            ZonedDateTime acknowledgedAt)
            throws UnregisteredEntityException, UnregisteredTransitionException;

    default <E, ID> CompletableFuture<Boolean> append(
            Class<E> type,
            ID id,
            Transition<E, ID> transition,
            ZonedDateTime occurredAt)
            throws UnregisteredEntityException, UnregisteredTransitionException {

        return append(type, id, transition, occurredAt, ZonedDateTime.now());
    }

    <E, ID> CompletableFuture<Entity<E, ID>> get(Class<E> type, ID id) throws UnregisteredEntityException;
    <E, ID> CompletableFuture<Entity<E, ID>> getAt(Class<E> type, ID id, long version)
            throws UnregisteredEntityException;

    <E, ID> EventStream<E, ID> getEventStream(Class<E> type, ID id) throws UnregisteredEntityException;

    <E, ID> CompletableFuture<Void> purge(Class<E> type, ID id)
            throws UnregisteredEntityException, UnsupportedOperationException;

    <E, ID> CompletableFuture<Void> snapshot(Class<E> type, ID id, long index) throws UnregisteredEntityException;
    <E, ID> CompletableFuture<Void> deleteSnapshot(Class<E> type, ID id, long index)
            throws UnregisteredEntityException, UnsupportedOperationException;

    // todo: return LazyLoadEntity implementation instead of plain stringified output
    CompletableFuture<List<StoredEntity>> getStoredEntities(long skip, long size) throws UnsupportedOperationException;
    CompletableFuture<Long> countStoredEntities() throws UnsupportedOperationException;

    boolean supportsBrowsing();
    boolean supportsPurge();
    boolean supportsSnapshotDeletion();

    <E, ID> EntityDirector addEntityListener(Class<E> type, EntityListener<E, ID> listener);
    <E, ID> EntityDirector removeEntityListener(Class<E> type, EntityListener<E, ID> listener);

    EntityDirector addEntityListener(WildcardListener listener);
    EntityDirector removeEntityListener(WildcardListener listener);
}
