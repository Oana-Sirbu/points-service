package de.rakuten.points.mapper;

import de.rakuten.points.domain.PointsTransactionDTO;
import de.rakuten.points.model.PointsTransaction;
import org.springframework.stereotype.Component;

@Component
public class PointsTransactionMapper extends Mapper<PointsTransaction, PointsTransactionDTO> {
  @Override
  public Class<PointsTransaction> getEntityClass() {
    return PointsTransaction.class;
  }

  @Override
  public Class<PointsTransactionDTO> getDtoClass() {
    return PointsTransactionDTO.class;
  }
}
