package de.rakuten.points.controller;

import de.rakuten.points.domain.PointsTransactionDTO;
import de.rakuten.points.service.impl.PointsBalanceServiceImpl;
import de.rakuten.points.service.impl.PointsTransactionServiceImpl;
import de.rakuten.points.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;

import static de.rakuten.points.util.TestUtils.getPointsTransactionDTO;
import static de.rakuten.points.util.TestUtils.restTemplateSetup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PointsTransactionControllerTest {
  @InjectMocks private PointsTransactionController controller;

  @Mock private PointsTransactionServiceImpl pointsTransactionService;
  @Mock private PointsBalanceServiceImpl pointsBalanceService;
  @Mock private ProductServiceImpl productService;
  @Mock private RestTemplate restTemplate;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller =
        new PointsTransactionController(
            pointsTransactionService, pointsBalanceService, productService);
  }

  @Test
  public void getPointsTransaction_pointsTransactionId_responseStatusOK() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.findById(pointsTransaction.getId()))
        .thenReturn(pointsTransaction);

    ResponseEntity<PointsTransactionDTO> response =
        controller.getPointsTransaction(pointsTransaction.getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(pointsTransaction.getId(), response.getBody().getId());
  }

  @Test
  public void putPointsTransaction_pointsTransactionIdAndPointsTransactionDTO_responseStatusOK() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.update(any())).thenReturn(pointsTransaction);
    when(pointsTransactionService.findById(any())).thenReturn(pointsTransaction);

    restTemplateSetup(restTemplate);

    ResponseEntity<PointsTransactionDTO> response =
        controller.putPointsTransaction(pointsTransaction.getId(), pointsTransaction);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(pointsTransaction, response.getBody());
  }

  @Test
  public void deletePointsTransaction_pointsTransactionId_responseStatusOK() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.findById(any())).thenReturn(pointsTransaction);
    restTemplateSetup(restTemplate);

    ResponseEntity<PointsTransactionDTO> response =
        controller.deletePointsTransaction(pointsTransaction.getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void createPointsTransaction_pointsTransactionDTO_responseStatusCreated() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.save(any())).thenReturn(pointsTransaction);

    restTemplateSetup(restTemplate);

    ResponseEntity<PointsTransactionDTO> response =
        controller.createPointsTransaction(pointsTransaction);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(pointsTransaction, response.getBody());
  }

  @Test
  public void createPointsTransaction_pointsTransactionDTO_throwException() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    pointsTransaction.getOrder().setCreatedAt("2019-09-26");
    when(pointsTransactionService.save(any())).thenReturn(pointsTransaction);

    restTemplateSetup(restTemplate);

    assertThrows(
        ConstraintViolationException.class,
        () -> controller.createPointsTransaction(pointsTransaction));
  }
}