package me.etki.es;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface EventStream<E, ID> {

    CompletableFuture<List<Event<E, ID>>> get(long skip, long size);

    CompletableFuture<Long> count();

    default CompletableFuture<Void> replay(long skip, long size, Consumer<Event<E, ID>> consumer) {
        return get(skip, size).thenAccept(events -> events.forEach(consumer));
    }

    CompletableFuture<Boolean> append(Transition<E, ID> transition, Instant timestamp);

    default CompletableFuture<Boolean> append(Event<E, ID> event) {
        return append(event.getTransition(), event.getOccurredAt());
    }
}
