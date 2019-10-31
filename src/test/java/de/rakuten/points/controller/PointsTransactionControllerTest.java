package de.rakuten.points.controller;

import de.rakuten.points.domain.OrderDTO;
import de.rakuten.points.domain.PointsTransactionDTO;
import de.rakuten.points.service.impl.PointsBalanceServiceImpl;
import de.rakuten.points.service.impl.PointsTransactionServiceImpl;
import de.rakuten.points.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.validation.*;
import java.util.Set;

import static de.rakuten.points.util.TestUtils.getActiveCampaignDTOList;
import static de.rakuten.points.util.TestUtils.getPointsTransactionDTO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointsTransactionControllerTest {
  @InjectMocks private PointsTransactionController controller;
  @Mock private PointsTransactionServiceImpl pointsTransactionService;
  @Mock private PointsBalanceServiceImpl pointsBalanceService;
  @Mock private ProductServiceImpl productService;

  @Mock(answer = Answers.RETURNS_DEEP_STUBS)
  private RestTemplate restTemplate;

  @Test
  public void getPointsTransaction_pointsTransactionId_responseStatusOK() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.findById(pointsTransaction.getId()))
        .thenReturn(pointsTransaction);

    ResponseEntity<PointsTransactionDTO> response =
        controller.getPointsTransaction(pointsTransaction.getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(pointsTransaction, response.getBody());
  }

  @Test
  public void putPointsTransaction_pointsTransactionIdAndPointsTransactionDTO_responseStatusOK() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.update(any())).thenReturn(pointsTransaction);
    when(pointsTransactionService.findById(any())).thenReturn(pointsTransaction);

    restTemplateSetup();

    ResponseEntity<PointsTransactionDTO> response =
        controller.putPointsTransaction(pointsTransaction.getId(), pointsTransaction);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(pointsTransaction, response.getBody());
  }

  @Test
  public void deletePointsTransaction_pointsTransactionId_responseStatusOK() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.findById(any())).thenReturn(pointsTransaction);

    restTemplateSetup();

    ResponseEntity<PointsTransactionDTO> response =
        controller.deletePointsTransaction(pointsTransaction.getId());

    assertEquals(HttpStatus.OK, response.getStatusCode());
  }

  @Test
  public void createPointsTransaction_pointsTransactionDTO_responseStatusCreated() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    when(pointsTransactionService.save(any())).thenReturn(pointsTransaction);

    restTemplateSetup();

    ResponseEntity<PointsTransactionDTO> response =
        controller.createPointsTransaction(pointsTransaction);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertEquals(pointsTransaction, response.getBody());
  }

  @Test
  public void createPointsTransaction_pointsTransactionDTO_throwException() {
    PointsTransactionDTO pointsTransaction = getPointsTransactionDTO();
    pointsTransaction.getOrder().setCreatedAt("2019-09-26");

    restTemplateSetup();

    assertThrows(
        ConstraintViolationException.class,
        () -> controller.createPointsTransaction(pointsTransaction));
  }

  @Test
  public void verifyOrderDto_invalidCustomerEmail_returnViolation() {
    OrderDTO pointsTransactionDTO = getPointsTransactionDTO().getOrder();
    pointsTransactionDTO.setCreatedAt("");

    ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    Validator validator = validatorFactory.getValidator();
    Set<ConstraintViolation<OrderDTO>> violations = validator.validate(pointsTransactionDTO);

    assertFalse(violations.isEmpty());
  }

  private void restTemplateSetup() {
    when(restTemplate.exchange(
            anyString(), any(HttpMethod.class), any(), any(ParameterizedTypeReference.class)))
        .thenReturn(new ResponseEntity(getActiveCampaignDTOList(), HttpStatus.OK));
  }
}
