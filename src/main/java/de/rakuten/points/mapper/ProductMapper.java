package de.rakuten.points.mapper;

import de.rakuten.points.domain.ProductDTO;
import de.rakuten.points.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper extends Mapper<Product, ProductDTO> {
  @Override
  public Class<Product> getEntityClass() {
    return Product.class;
  }

  @Override
  public Class<ProductDTO> getDtoClass() {
    return ProductDTO.class;
  }
}
