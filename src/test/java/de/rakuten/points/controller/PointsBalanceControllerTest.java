package de.rakuten.points.controller;

import de.rakuten.points.domain.PointsBalanceDTO;
import de.rakuten.points.service.impl.PointsBalanceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static de.rakuten.points.util.TestUtils.getPointsBalanceDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PointsBalanceControllerTest {
  @InjectMocks private PointsBalanceController controller;

  @Mock private PointsBalanceServiceImpl service;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    controller = new PointsBalanceController(service);
  }

  @Test
  public void getPointsBalance_validEmail_responseStatusOk() {
    when(service.getByCustomerEmail(any())).thenReturn(getPointsBalanceDTO());

    ResponseEntity<PointsBalanceDTO> response =
        controller.getPointsBalance(getPointsBalanceDTO().getCustomerEmail());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(getPointsBalanceDTO().getCustomerEmail(), response.getBody().getCustomerEmail());
  }
}
