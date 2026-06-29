package com.shop.product_service.exception;

/**
 * A custom exception we throw when a product with the requested id does not exist.
 * In PR 3 we'll add a global handler that turns this into a clean 404 response.
 */
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("Product not found with id: " + id);
    }
}
