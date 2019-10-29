package de.rakuten.points.mapper;

import de.rakuten.points.domain.OrderDTO;
import de.rakuten.points.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper extends Mapper<Order, OrderDTO> {
  @Override
  public Class<Order> getEntityClass() {
    return Order.class;
  }

  @Override
  public Class<OrderDTO> getDtoClass() {
    return OrderDTO.class;
  }
}
