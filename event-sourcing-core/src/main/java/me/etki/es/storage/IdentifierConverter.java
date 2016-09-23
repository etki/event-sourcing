package me.etki.es.storage;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface IdentifierConverter<ID> {

    String encode(ID id);
    ID decode(String encoded);
}
