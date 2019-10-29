package de.rakuten.points.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO implements Serializable {
  private String id;

  @NotNull private Integer quantity;

  @NotNull @Valid private ProductDTO product;
}
