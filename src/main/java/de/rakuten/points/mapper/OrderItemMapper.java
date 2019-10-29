package de.rakuten.points.mapper;

import de.rakuten.points.domain.OrderItemDTO;
import de.rakuten.points.model.OrderItem;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper extends Mapper<OrderItem, OrderItemDTO> {
  @Override
  public Class<OrderItem> getEntityClass() {
    return OrderItem.class;
  }

  @Override
  public Class<OrderItemDTO> getDtoClass() {
    return OrderItemDTO.class;
  }
}
