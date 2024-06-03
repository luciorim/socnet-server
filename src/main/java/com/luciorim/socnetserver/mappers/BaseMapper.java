package com.luciorim.socnetserver.mappers;

import java.util.List;

public interface BaseMapper<E, D> {
    D toDto(E entity);

    E toEntity(D dto);

    List<D> toDto(List<E> entities);
}
