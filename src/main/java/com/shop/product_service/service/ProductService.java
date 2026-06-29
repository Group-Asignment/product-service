package com.shop.product_service.service;

import com.shop.product_service.dto.ProductRequest;
import com.shop.product_service.dto.ProductResponse;

/**
 * The service INTERFACE describes WHAT the business logic can do,
 * without saying HOW. The controller depends on this interface, not on the
 * concrete class. This follows the "D" in SOLID (Dependency Inversion) and
 * makes the code easy to test and swap out.
 */
public interface ProductService {

    ProductResponse createProduct(ProductRequest request);

    ProductResponse getProductById(Long id);

    void deleteProduct(Long id);
}
