package de.rakuten.points.service.impl;

import de.rakuten.points.domain.PointsBalanceDTO;
import de.rakuten.points.mapper.PointsBalanceMapper;
import de.rakuten.points.repository.PointsBalanceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

import static de.rakuten.points.util.TestUtils.getPointsBalance;
import static de.rakuten.points.util.TestUtils.getPointsBalanceDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PointsBalanceServiceImplTest {
  @InjectMocks private PointsBalanceServiceImpl pointsBalanceService;
  @Mock private PointsBalanceRepository repository;
  @Mock private PointsBalanceMapper mapper;

  @Test
  public void getByCustomerEmail_validEmail_equalsDto() {
    PointsBalanceDTO pointsBalance = getPointsBalanceDTO();
    when(repository.getByCustomerEmail(anyString()))
        .thenReturn(Optional.ofNullable(getPointsBalance()));
    when(mapper.convertToDto(any())).thenReturn(pointsBalance);

    PointsBalanceDTO response =
        pointsBalanceService.getByCustomerEmail(pointsBalance.getCustomerEmail());

    assertEquals(pointsBalance, response);
  }

  @Test
  public void getByCustomerEmail_invalidEmail_throwException() {
    PointsBalanceDTO pointsBalance = getPointsBalanceDTO();
    when(repository.getByCustomerEmail(anyString())).thenReturn(Optional.ofNullable(null));

    assertThrows(
        ResourceNotFoundException.class,
        () -> pointsBalanceService.getByCustomerEmail(pointsBalance.getCustomerEmail()));
  }
}
