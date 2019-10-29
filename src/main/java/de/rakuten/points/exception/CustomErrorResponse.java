package de.rakuten.points.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static de.rakuten.points.commons.Constants.CUSTOM_ERROR_DATE_FORMAT;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomErrorResponse {
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CUSTOM_ERROR_DATE_FORMAT)
  private LocalDateTime timestamp;

  private int status;
  private String message;
}
