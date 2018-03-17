package me.etki.es.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class CompletableFutures {

    public static final CompletableFuture<Void> VOID = completed(null);
    public static final CompletableFuture<Boolean> FALSE = completed(false);
    public static final CompletableFuture<Boolean> TRUE = completed(true);

    private CompletableFutures() {
        // static helpers only
    }

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
        } catch (Exception exception) {
            return exceptional(exception);
        }
    }

    public static <T> CompletableFuture<T> pipe(CompletableFuture<T> source, CompletableFuture<T> target) {
        source.thenApply(target::complete).exceptionally(target::completeExceptionally);
        return target;
    }
}
