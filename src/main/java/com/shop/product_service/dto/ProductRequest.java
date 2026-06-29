package com.shop.product_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

/**
 * DTO = Data Transfer Object.
 * This is the shape of the JSON a client SENDS when creating a product.
 * We keep it separate from the Product entity so the API contract and the
 * database table can change independently (clean architecture / good practice).
 * The validation annotations reject bad input automatically.
 */
@Data
public class ProductRequest {

    @NotBlank(message = "name is required")
    private String name;

    @NotNull(message = "unitPrice is required")
    @Positive(message = "unitPrice must be greater than 0")
    private Double unitPrice;

    private String description;   // optional

    private String category;      // optional

    @PositiveOrZero(message = "stock cannot be negative")
    private Integer stock;        // optional
}
