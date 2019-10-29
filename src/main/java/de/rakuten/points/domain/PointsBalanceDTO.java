package de.rakuten.points.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PointsBalanceDTO implements Serializable {
  private String id;

  private String customerEmail;

  private Integer points;
}
