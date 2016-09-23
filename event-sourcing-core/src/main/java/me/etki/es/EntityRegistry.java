package me.etki.es;

import me.etki.es.exception.UnregisteredEntityException;

import java.util.HashMap;
import java.util.Map;

/**
 * todo: duplicated entity type handling
 *
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EntityRegistry {

    private final Map<Class, EntityDescriptor> classMapping = new HashMap<>();
    private final Map<String, EntityDescriptor> typeMapping = new HashMap<>();

    public synchronized <E, ID> EntityDescriptor<E, ID> getDescriptor(Class<E> entityClass) {
        // safe by design until (todo) someone starts poking with wrong ID class
        EntityDescriptor<E, ID> registration = (EntityDescriptor<E, ID>) classMapping.get(entityClass);
        if (registration == null) {
            throw new UnregisteredEntityException("Entity " + entityClass + " has been polled prior to registration");
        }
        return registration;
    }

    public synchronized <E, ID> EntityDescriptor<E, ID> getDescriptor(String entityType) {
        // totally not safe :shruggie: todo
        EntityDescriptor<E, ID> registration = (EntityDescriptor<E, ID>) typeMapping.get(entityType);
        if (registration == null) {
            throw new UnregisteredEntityException("Entity " + entityType + " has been polled prior to registration");
        }
        return registration;
    }

    public synchronized <E, ID> EntityRegistry register(EntityDescriptor<E, ID> registration) {
        classMapping.put(registration.getEntityClass(), registration);
        typeMapping.put(registration.getEntityType(), registration);
        return this;
    }
}
