package de.rakuten.points.util;

import de.rakuten.points.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TestUtils {
  public static PointsBalanceDTO getPointsBalanceDTO() {
    return new PointsBalanceDTO()
        .builder()
        .id("945afe2b-2847-4a95-ad79-004462d2c76")
        .customerEmail("customer@gmail.com")
        .points(0)
        .build();
  }

  public static PointsTransactionDTO getPointsTransactionDTO() {
    List<OrderItemDTO> items = new ArrayList<>();
    items.add(
        new OrderItemDTO()
            .builder()
            .id("0150e3ab-30b0-4ca5-affb-d8a33e70525d")
            .product(
                new ProductDTO()
                    .builder()
                    .id("0150e3ab-30b0-4ca5-affb-d8a33e70525c")
                    .name("IPhone 8S")
                    .price(299.5)
                    .build())
            .quantity(1)
            .build());
    items.add(
        new OrderItemDTO()
            .builder()
            .id("0150e3ab-30b0-4ca5-affb-d8a33e70525d")
            .product(
                new ProductDTO()
                    .builder()
                    .id("0150e3ab-30b0-4ca5-affb-d8a33e70525q")
                    .name("IPhone 7S")
                    .price(199.5)
                    .build())
            .quantity(1)
            .build());

    return new PointsTransactionDTO()
        .builder()
        .id("0150e3ab-30b0-4ca5-affb-d8a33e70525d")
        .order(
            new OrderDTO()
                .builder()
                .id("0150e3ab-30b0-4ca5-affb-d8a33e70525d")
                .customerEmail("customer@gmail.com")
                .createdAt("2019-12-26")
                .items(items)
                .build())
        .build();
  }

  public static List<CampaignDTO> getActiveCampaignDTOList() {
    List<ProductDTO> products = new ArrayList<>();
    products.add(
        new ProductDTO()
            .builder()
            .id("0150e3ab-30b0-4ca5-affb-d8a33e70525c")
            .name("IPhone 8S")
            .price(299.5)
            .build());

    List<CampaignDTO> campaigns = new ArrayList<>();
    campaigns.add(
        new CampaignDTO()
            .builder()
            .id("945afe2b-2847-4a95-ad79-004462d2c76")
            .name("Cellphone special offers")
            .startDate("2019-12-26T00:00:00.000Z")
            .endDate("2019-12-29T00:00:00.000Z")
            .points(10)
            .products(products)
            .build());
    return campaigns;
  }
}
