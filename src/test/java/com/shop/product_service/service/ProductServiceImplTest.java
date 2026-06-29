package com.shop.product_service.service;

import com.shop.product_service.dto.ProductRequest;
import com.shop.product_service.dto.ProductResponse;
import com.shop.product_service.exception.ProductNotFoundException;
import com.shop.product_service.model.Product;
import com.shop.product_service.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the business logic in ProductServiceImpl.
 * We use Mockito to create a FAKE repository, so these tests are fast and
 * never touch a real database. @InjectMocks puts the fake repository into the service.
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;   // fake DB layer

    @InjectMocks
    private ProductServiceImpl productService;      // the class under test

    @Test
    void createProduct_savesAndReturnsResponse() {
        // given: a request and what the repository will return after saving
        ProductRequest request = new ProductRequest();
        request.setName("Keyboard");
        request.setUnitPrice(2500.0);
        request.setStock(10);

        Product saved = new Product(1L, "Keyboard", 2500.0, null, null, 10);
        when(productRepository.save(any(Product.class))).thenReturn(saved);

        // when
        ProductResponse response = productService.createProduct(request);

        // then
        assertThat(response.getProductId()).isEqualTo(1L);
        assertThat(response.getName()).isEqualTo("Keyboard");
        assertThat(response.getUnitPrice()).isEqualTo(2500.0);
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getProductById_whenExists_returnsProduct() {
        Product product = new Product(1L, "Mouse", 1500.0, null, "Electronics", 50);
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(1L);

        assertThat(response.getName()).isEqualTo("Mouse");
        assertThat(response.getCategory()).isEqualTo("Electronics");
    }

    @Test
    void getProductById_whenMissing_throwsNotFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(99L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("99");
    }

    @Test
    void deleteProduct_whenExists_deletes() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.deleteProduct(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    void deleteProduct_whenMissing_throwsNotFound() {
        when(productRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> productService.deleteProduct(99L))
                .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, never()).deleteById(anyLong());
    }
}
