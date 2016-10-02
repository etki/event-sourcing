package me.etki.es.container;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EntityId<ID> {

    private final ID id;
    private final String encodedId;

    public EntityId(ID id, String encodedId) {
        this.id = id;
        this.encodedId = encodedId;
    }

    public ID getId() {
        return id;
    }

    public String getEncodedId() {
        return encodedId;
    }

    // hooray, i've saved couple of bytes

    public static <ID> EntityId<ID> of(ID id, String encodedId) {
        return new EntityId<>(id, encodedId);
    }
}
