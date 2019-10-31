package de.rakuten.points.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PointsBalanceDTO {
  private String id;

  private String customerEmail;

  private Integer points;
}
