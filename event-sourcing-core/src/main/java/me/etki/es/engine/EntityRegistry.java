package me.etki.es.engine;

import me.etki.es.container.EntityDescriptor;
import me.etki.es.container.EntityType;
import me.etki.es.exception.DuplicatedEntityException;
import me.etki.es.exception.UnregisteredEntityException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EntityRegistry {

    private final Map<Class, EntityDescriptor> classMapping = new HashMap<>();
    private final Map<String, EntityDescriptor> typeMapping = new HashMap<>();

    public <E, ID> EntityDescriptor<E, ID> getDescriptor(Class<E> entityClass) throws UnregisteredEntityException {
        // safe by design until (todo) someone starts poking with wrong ID class
        EntityDescriptor<E, ID> registration = (EntityDescriptor<E, ID>) classMapping.get(entityClass);
        if (registration == null) {
            throw new UnregisteredEntityException("Entity " + entityClass + " has been polled prior to registration");
        }
        return registration;
    }

    public <E, ID> EntityDescriptor<E, ID> getDescriptor(String entityType) throws UnregisteredEntityException {
        // totally not safe :shruggie: todo
        EntityDescriptor<E, ID> registration = (EntityDescriptor<E, ID>) typeMapping.get(entityType);
        if (registration == null) {
            throw new UnregisteredEntityException("Entity " + entityType + " has been polled prior to registration");
        }
        return registration;
    }

    public <E, ID> EntityRegistry register(EntityDescriptor<E, ID> registration) throws DuplicatedEntityException {
        if (typeMapping.containsKey(registration.getEntityType().getEntityType())) {
            EntityDescriptor descriptor = typeMapping.get(registration.getEntityType().getEntityType());
            String message = "Tried to register entity " + registration.getEntityType() + ", but that type " +
                    "has already been occupied by entity " + descriptor.getEntityType();
            throw new DuplicatedEntityException(message);
        }
        if (classMapping.containsKey(registration.getEntityType().getEntityClass())) {
            String message = "Tried to register entity " + registration.getEntityType() + " twice";
            throw new DuplicatedEntityException(message);
        }
        classMapping.put(registration.getEntityType().getEntityClass(), registration);
        typeMapping.put(registration.getEntityType().getEntityType(), registration);
        return this;
    }

    public <E> EntityRegistry removeDescriptor(Class<E> entityClass) {
        Optional
                .ofNullable(classMapping.remove(entityClass))
                .map(EntityDescriptor::getEntityType)
                .map(EntityType::getEntityType)
                .ifPresent(typeMapping::remove);
        return this;
    }

    public EntityRegistry removeDescriptor(String type) {
        Optional
                .ofNullable(typeMapping.remove(type))
                .map(EntityDescriptor::getEntityType)
                .map(EntityType::getEntityClass)
                .ifPresent(classMapping::remove);
        return this;
    }
}
