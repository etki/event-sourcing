package me.etki.es.container;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EntityType<E> {

    private final Class<E> entityClass;
    private final String entityType;

    public EntityType(Class<E> entityClass, String entityType) {
        this.entityClass = entityClass;
        this.entityType = entityType;
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }

    public String getEntityType() {
        return entityType;
    }
}
