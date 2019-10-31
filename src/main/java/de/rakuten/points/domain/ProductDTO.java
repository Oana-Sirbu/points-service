package de.rakuten.points.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class ProductDTO {
  @NotNull private String id;

  private String name;

  private Double price;
}
