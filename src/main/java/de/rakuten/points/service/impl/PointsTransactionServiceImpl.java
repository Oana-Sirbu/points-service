package de.rakuten.points.service.impl;

import de.rakuten.points.domain.PointsTransactionDTO;
import de.rakuten.points.mapper.PointsTransactionMapper;
import de.rakuten.points.model.PointsTransaction;
import de.rakuten.points.repository.PointsTransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class PointsTransactionServiceImpl
    extends ServiceImpl<
        PointsTransaction,
        PointsTransactionDTO,
        PointsTransactionRepository,
        PointsTransactionMapper> {
  private final PointsTransactionRepository pointsTransactionRepository;
  private final PointsTransactionMapper pointsTransactionMapper;

  protected PointsTransactionServiceImpl(
      PointsTransactionRepository repository, PointsTransactionMapper mapper) {
    super(repository, mapper);
    this.pointsTransactionRepository = repository;
    this.pointsTransactionMapper = mapper;
  }
}
