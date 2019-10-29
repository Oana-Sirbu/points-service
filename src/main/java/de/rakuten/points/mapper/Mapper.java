package de.rakuten.points.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class Mapper<T, S> {
  @Autowired private ModelMapper modelMapper;

  public abstract Class<T> getEntityClass();

  public abstract Class<S> getDtoClass();

  public S convertToDto(T entity) {
    return modelMapper.map(entity, getDtoClass());
  }

  public T convertToEntity(S dto) {
    return modelMapper.map(dto, getEntityClass());
  }
}
