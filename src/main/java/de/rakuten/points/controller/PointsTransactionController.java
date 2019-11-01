package de.rakuten.points.controller;

import de.rakuten.points.domain.CampaignDTO;
import de.rakuten.points.domain.PointsTransactionDTO;
import de.rakuten.points.service.impl.PointsBalanceServiceImpl;
import de.rakuten.points.service.impl.PointsTransactionServiceImpl;
import de.rakuten.points.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static de.rakuten.points.commons.Constants.*;

@Slf4j
@Validated
@RestController
@RequestMapping("/pointsapi")
public class PointsTransactionController {
  private PointsTransactionServiceImpl pointsTransactionService;
  private PointsBalanceServiceImpl pointsBalanceService;
  private ProductServiceImpl productService;
  private RestTemplate restTemplate;

  @Value("${campaign.api.address}")
  private String campaignURL;

  @Autowired
  public PointsTransactionController(
      PointsTransactionServiceImpl pointsTransactionService,
      PointsBalanceServiceImpl pointsBalanceService,
      ProductServiceImpl productService,
      RestTemplate restTemplate) {
    this.pointsTransactionService = pointsTransactionService;
    this.pointsBalanceService = pointsBalanceService;
    this.productService = productService;
    this.restTemplate = restTemplate;
  }

  public List<CampaignDTO> getActiveCampaigns(PointsTransactionDTO pointsTransactionDTO) {
    String resourceUrl =
        campaignURL + "/campaign/active/" + pointsTransactionDTO.getOrder().getCreatedAt();
    ResponseEntity<List<CampaignDTO>> responseEntity =
        restTemplate.exchange(
            resourceUrl,
            HttpMethod.GET,
            new HttpEntity<>(new ArrayList<>()),
            new ParameterizedTypeReference<List<CampaignDTO>>() {});
    return responseEntity.getBody();
  }

  @GetMapping("/pointstransaction/{id}")
  ResponseEntity<PointsTransactionDTO> getPointsTransaction(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = UUID_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
          String id) {
    PointsTransactionDTO pointsTransactionDTO = pointsTransactionService.findById(id);
    return new ResponseEntity<>(pointsTransactionDTO, HttpStatus.OK);
  }

  @PutMapping("/pointstransaction/{id}")
  ResponseEntity<PointsTransactionDTO> putPointsTransaction(
      @PathVariable
          @Valid
          @NotNull
          @Pattern(regexp = UUID_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
          String id,
      @RequestBody @Valid PointsTransactionDTO pointsTransactionDTO) {
    validateProducts(pointsTransactionDTO);

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
          @Pattern(regexp = UUID_REGEX_PATTERN, message = BAD_INPUT_ERROR_MESSAGE)
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
    validateProducts(pointsTransactionDTO);

    if (!validatePointsTransaction(pointsTransactionDTO)) {
      log.error("Invalid created at date");
      throw new ConstraintViolationException(BAD_INPUT_ERROR_MESSAGE, new HashSet<>());
    }

    PointsTransactionDTO result = pointsTransactionService.save(pointsTransactionDTO);
    pointsBalanceService.addPointsToCustomer(
        productService, pointsTransactionDTO, getActiveCampaigns(pointsTransactionDTO));
    return new ResponseEntity<>(result, HttpStatus.CREATED);
  }

  private void validateProducts(PointsTransactionDTO pointsTransactionDTO) {
    pointsTransactionDTO
        .getOrder()
        .getItems()
        .forEach(item -> productService.findById(item.getProduct().getId()));
  }

  private boolean validatePointsTransaction(PointsTransactionDTO pointsTransactionDTO) {
    return LocalDate.now()
        .isBefore(
            LocalDate.parse(
                pointsTransactionDTO.getOrder().getCreatedAt(),
                DateTimeFormatter.ofPattern(DATE_FORMAT)));
  }
}
