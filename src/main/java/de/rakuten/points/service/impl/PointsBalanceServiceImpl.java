package de.rakuten.points.service.impl;

import de.rakuten.points.domain.*;
import de.rakuten.points.mapper.PointsBalanceMapper;
import de.rakuten.points.model.PointsBalance;
import de.rakuten.points.repository.PointsBalanceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static de.rakuten.points.commons.Constants.NOT_FOUND_ERROR_MESSAGE;

@Slf4j
@Service
public class PointsBalanceServiceImpl
    extends ServiceImpl<
        PointsBalance, PointsBalanceDTO, PointsBalanceRepository, PointsBalanceMapper> {
  private final PointsBalanceRepository pointsBalanceRepository;
  private PointsBalanceMapper mapper;

  protected PointsBalanceServiceImpl(
      PointsBalanceRepository repository, PointsBalanceMapper mapper) {
    super(repository, mapper);
    this.pointsBalanceRepository = repository;
    this.mapper = mapper;
  }

  public PointsBalanceDTO getByCustomerEmail(String customerEmail) {

    log.info("Finding points by the following customer email : {}", customerEmail);
    return mapper.convertToDto(
        pointsBalanceRepository
            .getByCustomerEmail(customerEmail)
            .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_ERROR_MESSAGE)));
  }

  private Integer getPointsForCustomer(
      ProductServiceImpl productService,
      PointsTransactionDTO pointsTransactionDTO,
      List<CampaignDTO> campaignDTOList) {

    log.info("Starting calculation points for customer");
    final Integer[] points = {0};

    pointsTransactionDTO
        .getOrder()
        .getItems()
        .forEach(
            orderItem ->
                campaignDTOList.forEach(
                    campaign -> points[0] += getPoints(productService, orderItem, campaign)));

    log.info("Points for customer : {}", points[0]);
    return points[0];
  }

  private int getPoints(
      ProductServiceImpl productService, OrderItemDTO orderItem, CampaignDTO campaign) {
    ProductDTO productDTO = productService.findById(orderItem.getProduct().getId());
    return existProductInCampaign(orderItem, campaign)
        ? ((int) (productDTO.getPrice() * orderItem.getQuantity()) * campaign.getPoints())
        : ((int) (productDTO.getPrice() * orderItem.getQuantity()));
  }

  private boolean existProductInCampaign(OrderItemDTO orderItem, CampaignDTO campaign) {
    return campaign.getProducts().stream()
        .anyMatch(product -> product.getName().equals(orderItem.getProduct().getName()));
  }

  public void addPointsToCustomer(
      ProductServiceImpl productService,
      PointsTransactionDTO pointsTransactionDTO,
      List<CampaignDTO> campaignDTOList) {

    log.info("Add points for customer");
    Integer points = getPointsForCustomer(productService, pointsTransactionDTO, campaignDTOList);
    putPointsToCustomer(pointsTransactionDTO.getOrder().getCustomerEmail(), points);
  }

  public void updatePointsToCustomer(
      ProductServiceImpl productService,
      PointsTransactionDTO oldPointsTransactionDTO,
      PointsTransactionDTO newPointsTransactionDTO,
      List<CampaignDTO> campaignDTOList) {

    log.info("Update points for customer");
    String oldEmail = oldPointsTransactionDTO.getOrder().getCustomerEmail();

    Integer points =
        -getPointsForCustomer(productService, oldPointsTransactionDTO, campaignDTOList);
    putPointsToCustomer(oldEmail, points);

    points = getPointsForCustomer(productService, newPointsTransactionDTO, campaignDTOList);
    putPointsToCustomer(oldEmail, points);
  }

  public void removePointsToCustomer(
      ProductServiceImpl productService,
      PointsTransactionDTO pointsTransactionDTO,
      List<CampaignDTO> campaignDTOList) {

    log.info("Remove points for customer");
    Integer points = -getPointsForCustomer(productService, pointsTransactionDTO, campaignDTOList);
    putPointsToCustomer(pointsTransactionDTO.getOrder().getCustomerEmail(), points);
  }

  private void putPointsToCustomer(String email, Integer points) {
    log.info("Starting put points to customer. Email : {} ", email);
    Optional<PointsBalance> pointsBalance = pointsBalanceRepository.getByCustomerEmail(email);
    if (pointsBalance.isPresent()) {
      PointsBalance pointsBalanceEntity = pointsBalance.get();
      pointsBalanceEntity.setPoints(pointsBalanceEntity.getPoints() + points);
      update(mapper.convertToDto(pointsBalanceEntity));
    } else {
      save(PointsBalanceDTO.builder().customerEmail(email).points(0).build());
    }
  }
}
