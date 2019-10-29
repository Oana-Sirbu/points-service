package de.rakuten.points.service.impl;

import de.rakuten.points.domain.OrderDTO;
import de.rakuten.points.mapper.OrderMapper;
import de.rakuten.points.model.Order;
import de.rakuten.points.repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends ServiceImpl<Order, OrderDTO, OrderRepository, OrderMapper> {
  private final OrderRepository orderRepository;
  private final OrderMapper orderMapper;

  protected OrderServiceImpl(OrderRepository repository, OrderMapper mapper) {
    super(repository, mapper);
    this.orderRepository = repository;
    this.orderMapper = mapper;
  }
}
