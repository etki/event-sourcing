package me.etki.es.store;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface IdentifierConverter<ID> {

    String encode(ID id);
    ID decode(String encoded);
}
