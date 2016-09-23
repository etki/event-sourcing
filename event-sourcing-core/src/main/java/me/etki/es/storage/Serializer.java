package me.etki.es.storage;

import java.io.IOException;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface Serializer {

    <T> String serialize(T item) throws IOException;
    <T> T deserialize(String serialized, Class<T> type) throws IOException;
}
