package me.etki.es.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class CompletableFutures {

    public static <T> CompletableFuture<T> completed(T item) {
        return CompletableFuture.completedFuture(item);
    }

    public static <T> CompletableFuture<T> exceptional(Throwable throwable) {
        CompletableFuture<T> future = new CompletableFuture<>();
        future.completeExceptionally(throwable);
        return future;
    }

    public static <T> CompletableFuture<T> wrapExecution(Callable<T> callable) {
        try {
            return completed(callable.call());
        } catch (Throwable throwable) {
            return exceptional(throwable);
        }
    }
}
