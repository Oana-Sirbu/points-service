package de.rakuten.points.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

import static de.rakuten.points.commons.Constants.BAD_INPUT_ERROR_MESSAGE;
import static de.rakuten.points.commons.Constants.NOT_FOUND_ERROR_MESSAGE;

@Slf4j
@ControllerAdvice
@RestController
public class CustomExceptionHandler {
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<CustomErrorResponse> handle(ResourceNotFoundException e) {
    CustomErrorResponse error = new CustomErrorResponse();
    error.setTimestamp(LocalDateTime.now());
    error.setMessage(e.getMessage());
    error.setStatus(HttpStatus.NOT_FOUND.value());

    log.error(NOT_FOUND_ERROR_MESSAGE);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<CustomErrorResponse> handle(ConstraintViolationException e) {
    CustomErrorResponse error = new CustomErrorResponse();
    error.setTimestamp(LocalDateTime.now());
    error.setMessage(e.getMessage());
    error.setStatus(HttpStatus.BAD_REQUEST.value());

    log.error(BAD_INPUT_ERROR_MESSAGE);
    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
