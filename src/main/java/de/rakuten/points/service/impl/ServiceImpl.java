package de.rakuten.points.service.impl;

import de.rakuten.points.mapper.Mapper;
import de.rakuten.points.service.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import static de.rakuten.points.commons.Constants.NOT_FOUND_ERROR_MESSAGE;

@Slf4j
public class ServiceImpl<T, S, R extends JpaRepository<T, String>, U extends Mapper<T, S>>
    implements Service<S> {
  private final R repository;
  private final U mapper;

  protected ServiceImpl(R repository, U mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  @Override
  public S save(S dto) {
    log.info("Saving the following information : {}", dto.toString());
    return mapper.convertToDto(repository.save(mapper.convertToEntity(dto)));
  }

  @Override
  public void deleteById(String id) {
    log.info("Deleting the information for the following id : {}", id);
    repository.deleteById(id);
  }

  @Override
  public S findById(String id) {
    log.info("Finding by the following id : {}", id);
    return mapper.convertToDto(
        repository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_ERROR_MESSAGE)));
  }

  @Override
  public S update(S dto) {
    log.info("Updating the following information : {}", dto.toString());
    return mapper.convertToDto(repository.saveAndFlush(mapper.convertToEntity(dto)));
  }
}
