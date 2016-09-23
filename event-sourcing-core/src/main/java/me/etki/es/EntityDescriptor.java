package me.etki.es;

import me.etki.es.storage.IdentifierConverter;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public interface EntityDescriptor<E, ID> {

    Class<E> getEntityClass();
    Class<ID> getIdClass();
    String getEntityType();
    IdentifierConverter<ID> getIdentifierConverter();
    Class<Transition<E, ID>> getEventClass(String eventType);
    String getEventType(Class<Transition<E, ID>> eventClass);

    EventDescriptor<E, ID> getEventDescriptor(Class<? extends Transition<E, ID>> eventClass);
    EventDescriptor<E, ID> getEventDescriptor(String eventType, int eventVersion);
}
