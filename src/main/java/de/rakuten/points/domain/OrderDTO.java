package de.rakuten.points.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO implements Serializable {
  private String id;

  @NotEmpty private String customerEmail;

  @NotEmpty private String createdAt;

  @NotNull @Valid private List<OrderItemDTO> items;
}
