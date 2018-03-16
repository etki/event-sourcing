package me.etki.es.container;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Etki {@literal <etki@etki.me>}
 * @version %I%, %G%
 * @since 0.1.0
 */
@RequiredArgsConstructor
@EqualsAndHashCode
public class EntityType<E> {

    @Getter
    private final Class<E> entityClass;
    @Getter
    private final String entityType;
}
