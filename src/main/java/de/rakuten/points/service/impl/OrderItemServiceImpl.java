package de.rakuten.points.service.impl;

import de.rakuten.points.domain.OrderItemDTO;
import de.rakuten.points.mapper.OrderItemMapper;
import de.rakuten.points.model.OrderItem;
import de.rakuten.points.repository.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl
    extends ServiceImpl<OrderItem, OrderItemDTO, OrderItemRepository, OrderItemMapper> {

  private final OrderItemMapper orderitemMapper;
  private OrderItemRepository orderitemRepository;

  public OrderItemServiceImpl(OrderItemRepository repository, OrderItemMapper mapper) {
    super(repository, mapper);
    this.orderitemRepository = repository;
    this.orderitemMapper = mapper;
  }
}
