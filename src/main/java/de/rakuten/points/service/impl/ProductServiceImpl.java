package de.rakuten.points.service.impl;

import de.rakuten.points.domain.ProductDTO;
import de.rakuten.points.mapper.ProductMapper;
import de.rakuten.points.model.Product;
import de.rakuten.points.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl
    extends ServiceImpl<Product, ProductDTO, ProductRepository, ProductMapper> {
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  protected ProductServiceImpl(ProductRepository repository, ProductMapper mapper) {
    super(repository, mapper);
    this.productRepository = repository;
    this.productMapper = mapper;
  }
}
