package de.rakuten.points.controller;

import de.rakuten.points.domain.PointsBalanceDTO;
import de.rakuten.points.service.impl.PointsBalanceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import static de.rakuten.points.commons.Constants.BAD_INPUT_ERROR_MESSAGE;

@Validated
@RestController
@RequestMapping("/pointsapi")
public class PointsBalanceController {
  private PointsBalanceServiceImpl pointsBalanceService;

  @Autowired
  public PointsBalanceController(PointsBalanceServiceImpl pointsBalanceService) {
    this.pointsBalanceService = pointsBalanceService;
  }

  @GetMapping("/pointsbalance/customer/{customeremail}")
  ResponseEntity<PointsBalanceDTO> getPointsBalance(
      @PathVariable @Valid @NotNull @Email(message = BAD_INPUT_ERROR_MESSAGE)
          String customerEmail) {
    PointsBalanceDTO pointsBalance = pointsBalanceService.getByCustomerEmail(customerEmail);
    return new ResponseEntity<>(pointsBalance, HttpStatus.OK);
  }
}
