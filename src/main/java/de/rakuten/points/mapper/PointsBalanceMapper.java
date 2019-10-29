package de.rakuten.points.mapper;

import de.rakuten.points.domain.PointsBalanceDTO;
import de.rakuten.points.model.PointsBalance;
import org.springframework.stereotype.Component;

@Component
public class PointsBalanceMapper extends Mapper<PointsBalance, PointsBalanceDTO> {
  @Override
  public Class<PointsBalance> getEntityClass() {
    return PointsBalance.class;
  }

  @Override
  public Class<PointsBalanceDTO> getDtoClass() {
    return PointsBalanceDTO.class;
  }
}
