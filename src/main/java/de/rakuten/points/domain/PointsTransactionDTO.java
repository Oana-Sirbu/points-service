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
public class PointsTransactionDTO implements Serializable {
  private String id;

  @NotNull @Valid private OrderDTO order;
}
