package me.etki.es.listener;

import me.etki.es.container.Entity;
import me.etki.es.container.Event;

import java.util.concurrent.CompletableFuture;

/**
 * Listener for all applied events.
 *
 * This listener, once registered, will be fed with all events that were successfully applied.
 *
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface WildcardListener {

    <E, ID> CompletableFuture<Void> accept(Entity<E, ID> entity, Event<E, ID> event);
}
