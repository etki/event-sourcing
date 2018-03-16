package me.etki.es.store;

import java.io.IOException;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface Serializer {

    <T> String serialize(T item) throws IOException;
    <T> T deserialize(String serialized, Class<T> type) throws IOException;
}
