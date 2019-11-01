package de.rakuten.points.controller;

import de.rakuten.points.domain.PointsBalanceDTO;
import de.rakuten.points.service.impl.PointsBalanceServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static de.rakuten.points.util.TestUtils.getPointsBalanceDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointsBalanceControllerTest {
  @InjectMocks private PointsBalanceController controller;
  @Mock private PointsBalanceServiceImpl service;

  @Test
  public void getPointsBalance_validEmail_responseStatusOk() {
    PointsBalanceDTO pointsBalanceDTO = getPointsBalanceDTO();
    when(service.getByCustomerEmail(any())).thenReturn(pointsBalanceDTO);

    ResponseEntity<PointsBalanceDTO> response =
        controller.getPointsBalance(pointsBalanceDTO.getCustomerEmail());

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(pointsBalanceDTO, response.getBody());
  }
}
