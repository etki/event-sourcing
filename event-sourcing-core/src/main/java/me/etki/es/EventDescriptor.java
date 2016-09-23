package me.etki.es;

/**
 * @author Etki {@literal <etki@etki.name>}
 * @version %I%, %G%
 * @since 0.1.0
 */
public class EventDescriptor<E, ID> {

    private final Class<? extends Transition<E, ID>> eventClass;
    private final String eventType;
    private final int eventVersion;
    private final Class<E> entityClass;

    public EventDescriptor(
            Class<? extends Transition<E, ID>> eventClass,
            String eventType,
            int eventVersion,
            Class<E> entityClass) {

        this.eventClass = eventClass;
        this.eventType = eventType;
        this.eventVersion = eventVersion;
        this.entityClass = entityClass;
    }

    public Class<? extends Transition<E, ID>> getEventClass() {
        return eventClass;
    }

    public String getEventType() {
        return eventType;
    }

    public int getEventVersion() {
        return eventVersion;
    }

    public Class<E> getEntityClass() {
        return entityClass;
    }
}
