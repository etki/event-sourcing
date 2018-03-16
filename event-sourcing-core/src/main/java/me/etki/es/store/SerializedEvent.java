package me.etki.es.store;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.ZonedDateTime;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@Accessors(chain = true)
public class SerializedEvent {

    @Getter
    @Setter
    private String entityType;
    @Getter
    @Setter
    private String entityId;
    @Getter
    @Setter
    private long index;
    @Getter
    @Setter
    private String transitionType;
    @Getter
    @Setter
    private int transitionVersion;
    @Getter
    @Setter
    private String serializedTransition;
    @Getter
    @Setter
    private ZonedDateTime occurredAt;
    @Getter
    @Setter
    private ZonedDateTime acknowledgedAt;
}
