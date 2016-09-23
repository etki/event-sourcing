package me.etki.es;

import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface EntityListener<E, ID> {

    CompletableFuture<Void> accept(Entity<E, ID> entity, Transition<E, ID> transition);
}
