package me.etki.es.container;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
public class EntityId<ID> {

    @Getter
    private final ID id;
    @Getter
    private final String encodedId;

    // hooray, i've saved couple of bytes

    public static <ID> EntityId<ID> of(ID id, String encodedId) {
        return new EntityId<>(id, encodedId);
    }
}
