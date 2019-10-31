package de.rakuten.points.util;

import de.rakuten.points.domain.*;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
  public static PointsBalanceDTO getPointsBalanceDTO() {
    return PointsBalanceDTO.builder()
        .id("945afe2b-2847-4a95-ad79-004462d2c76")
        .customerEmail("customer@gmail.com")
        .points(0)
        .build();
  }

  public static PointsTransactionDTO getPointsTransactionDTO() {
    List<OrderItemDTO> items = new ArrayList<>();
    items.add(
        OrderItemDTO.builder()
            .id("0150e3ab-30b0-4ca5-affb-d8a33e70525d")
            .product(
                ProductDTO.builder()
                    .id("0150e3ab-30b0-4ca5-affb-d8a33e70525c")
                    .name("IPhone 8S")
                    .price(299.5)
                    .build())
            .quantity(1)
            .build());
    items.add(
        OrderItemDTO.builder()
            .id("0150e3ab-30b0-4ca5-affb-d8a33e70525d")
            .product(
                ProductDTO.builder()
                    .id("0150e3ab-30b0-4ca5-affb-d8a33e70525q")
                    .name("IPhone 7S")
                    .price(199.5)
                    .build())
            .quantity(1)
            .build());

    return PointsTransactionDTO.builder()
        .id("0150e3ab-30b0-4ca5-affb-d8a33e70525d")
        .order(
            OrderDTO.builder()
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
        ProductDTO.builder()
            .id("0150e3ab-30b0-4ca5-affb-d8a33e70525c")
            .name("IPhone 8S")
            .price(299.5)
            .build());

    List<CampaignDTO> campaigns = new ArrayList<>();
    campaigns.add(
        CampaignDTO.builder()
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
