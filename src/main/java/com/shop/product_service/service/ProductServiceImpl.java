package com.shop.product_service.service;

import com.shop.product_service.dto.ProductRequest;
import com.shop.product_service.dto.ProductResponse;
import com.shop.product_service.exception.ProductNotFoundException;
import com.shop.product_service.model.Product;
import com.shop.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

/**
 * The actual business logic — the "HOW" behind the ProductService interface.
 * @Service tells Spring to manage this class and inject it where needed.
 */
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor injection: Spring automatically passes in the repository.
    // This is the recommended way (easier to test than @Autowired on a field).
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        // 1. Convert the incoming request DTO into a Product entity
        Product product = new Product();
        product.setName(request.getName());
        product.setUnitPrice(request.getUnitPrice());
        product.setDescription(request.getDescription());
        product.setCategory(request.getCategory());
        product.setStock(request.getStock());

        // 2. Save it to the database (repository returns the saved row, now with an id)
        Product saved = productRepository.save(product);

        // 3. Convert the saved entity back into a response DTO and return it
        return toResponse(saved);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        // findById returns an Optional; if empty, throw our custom not-found exception
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return toResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {
        // Make sure it exists first, so deleting a missing id gives a clear error
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }

    // Helper: maps a Product entity to a ProductResponse DTO
    private ProductResponse toResponse(Product product) {
        return new ProductResponse(
                product.getProductId(),
                product.getName(),
                product.getUnitPrice(),
                product.getDescription(),
                product.getCategory(),
                product.getStock()
        );
    }
}
