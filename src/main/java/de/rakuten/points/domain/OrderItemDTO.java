package de.rakuten.points.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class OrderItemDTO {
  private String id;

  @NotNull private Integer quantity;

  @NotNull @Valid private ProductDTO product;
}
