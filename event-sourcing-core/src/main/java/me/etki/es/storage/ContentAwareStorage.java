package me.etki.es.storage;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface ContentAwareStorage {

    CompletableFuture<List<StoredEntity>> getStoredEntities(long skip, long size);

}
