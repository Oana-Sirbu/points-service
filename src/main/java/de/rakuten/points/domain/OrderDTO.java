package de.rakuten.points.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class OrderDTO {
  private String id;

  @NotEmpty private String customerEmail;

  @NotEmpty private String createdAt;

  @NotNull @Valid private List<OrderItemDTO> items;
}
