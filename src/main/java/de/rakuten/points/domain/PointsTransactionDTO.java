package de.rakuten.points.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class PointsTransactionDTO {
  private String id;

  @NotNull @Valid private OrderDTO order;
}
