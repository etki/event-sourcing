package me.etki.es.container;

import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(of = {"entityType", "entityId", "entityVersion"})
public class Snapshot<E, ID> {

    @Setter
    @Getter
    private EntityType<E> entityType;
    @Setter
    @Getter
    private EntityId<ID> entityId;
    @Setter
    @Getter
    private long entityVersion = 0;
    @Setter
    @Getter
    private E entity;
    @Setter
    @Getter
    private ZonedDateTime transitionedAt;
    @Setter
    @Getter
    private ZonedDateTime transitionAcknowledgedAt;
    @Setter
    @Getter
    private ZonedDateTime takenAt;
}
