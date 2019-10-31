package de.rakuten.points.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
public class CampaignDTO {
  private String id;

  @NotEmpty private String name;

  @NotEmpty private String startDate;

  @NotEmpty private String endDate;

  @NotNull private Integer points;

  @NotNull @Valid private List<ProductDTO> products;
}
