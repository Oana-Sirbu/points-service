package de.rakuten.points.controller;

import de.rakuten.points.domain.CampaignDTO;
import de.rakuten.points.domain.PointsTransactionDTO;
import de.rakuten.points.service.impl.PointsBalanceServiceImpl;
import de.rakuten.points.service.impl.PointsTransactionServiceImpl;
import de.rakuten.points.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Arrays;
import java.util.List;

import static de.rakuten.points.commons.Constants.BAD_INPUT_ERROR_MESSAGE;
import static de.rakuten.points.commons.Constants.UUID_REGEX_PATTERN;

@Validated
@RestController
@RequestMapping("/pointsapi")
public class PointsTransactionController {
  private PointsTransactionServiceImpl pointsTransactionService;
  private PointsBalanceServiceImpl pointsBalanceService;
  private ProductServiceImpl productService;

  @Autowired
  public PointsTransactionController(
      PointsTransactionServiceImpl pointsTransactionService,
      PointsBalanceServiceImpl pointsBalanceService,
      ProductServiceImpl productService) {
    this.pointsTransactionService = pointsTransactionService;
    this.pointsBalanceService = pointsBalanceService;
    this.productService = productService;
  }

  private List<CampaignDTO> getActiveCampaigns(PointsTransactionDTO pointsTransactionDTO) {
    RestTemplate restTemplate = new RestTemplate();
    String resourceUrl = "http://localhost:8081/campaignapi/campaign/active/";
    ResponseEntity<CampaignDTO[]> response =
        restTemplate.getForEntity(
            resourceUrl + pointsTransactionDTO.getOrder().getCreatedAt(), CampaignDTO[].class);
    return Arrays.asList(response.getBody());
  }

  @GetMapping("/pointstransaction/{id}")
  ResponseEntity<PointsTransactionDTO> getPointsTransaction(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(
              regexp =
                  UUID_REGEX_PATTERN,
              message = BAD_INPUT_ERROR_MESSAGE)
          String id) {
    PointsTransactionDTO pointsTransactionDTO = pointsTransactionService.findById(id);
    return new ResponseEntity<>(pointsTransactionDTO, HttpStatus.OK);
  }

  @PutMapping("/pointstransaction/{id}")
  ResponseEntity<PointsTransactionDTO> putPointsTransaction(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(
              regexp =
                  UUID_REGEX_PATTERN,
              message = BAD_INPUT_ERROR_MESSAGE)
          String id,
      @RequestBody @Valid PointsTransactionDTO pointsTransactionDTO) {
    PointsTransactionDTO oldPointsTransactionDTO = pointsTransactionService.findById(id);
    PointsTransactionDTO newPointsTransactionDTO =
        pointsTransactionService.update(pointsTransactionDTO);

    pointsBalanceService.updatePointsToCustomer(
        productService,
        oldPointsTransactionDTO,
        newPointsTransactionDTO,
        getActiveCampaigns(oldPointsTransactionDTO));
    return new ResponseEntity<>(newPointsTransactionDTO, HttpStatus.OK);
  }

  @DeleteMapping("/pointstransaction/{id}")
  ResponseEntity<PointsTransactionDTO> deletePointsTransaction(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(
              regexp =
                  UUID_REGEX_PATTERN,
              message = BAD_INPUT_ERROR_MESSAGE)
          String id) {
    PointsTransactionDTO oldPointsTransactionDTO = pointsTransactionService.findById(id);
    pointsTransactionService.deleteById(id);
    pointsBalanceService.removePointsToCustomer(
        productService, oldPointsTransactionDTO, getActiveCampaigns(oldPointsTransactionDTO));
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/pointstransaction/")
  ResponseEntity<PointsTransactionDTO> createPointsTransaction(
      @RequestBody @Valid PointsTransactionDTO pointsTransactionDTO) {
    PointsTransactionDTO result = pointsTransactionService.save(pointsTransactionDTO);
    pointsBalanceService.addPointsToCustomer(
        productService, pointsTransactionDTO, getActiveCampaigns(pointsTransactionDTO));
    return new ResponseEntity<>(result, HttpStatus.OK);
  }
}
