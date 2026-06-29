package com.shop.product_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is the shape of the JSON we SEND BACK to the client.
 * It includes the generated productId (which the request didn't have).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String name;
    private Double unitPrice;
    private String description;
    private String category;
    private Integer stock;
}
