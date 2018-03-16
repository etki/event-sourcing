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
@EqualsAndHashCode(of = {"id", "type", "version"})
public class Entity<E, ID> {

    @Getter
    @Setter
    private EntityId<ID> id;
    @Getter
    @Setter
    private EntityType<E> type;
    @Getter
    @Setter
    private E entity;
    @Getter
    @Setter
    private Long version;
    @Getter
    @Setter
    private Long snapshotVersion;
    @Getter
    @Setter
    private ZonedDateTime transitionedAt;
    @Getter
    @Setter
    private ZonedDateTime transitionAcknowledgedAt;
    @Getter
    @Setter
    private ZonedDateTime snapshotTakenAt;
}
