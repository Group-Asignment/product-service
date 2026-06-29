package com.shop.product_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop.product_service.dto.ProductRequest;
import com.shop.product_service.dto.ProductResponse;
import com.shop.product_service.exception.ProductNotFoundException;
import com.shop.product_service.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Tests for the HTTP layer (ProductController).
 * @WebMvcTest loads ONLY the web layer (not the database), and we replace the
 * real service with a mock using @MockitoBean. MockMvc lets us send fake HTTP
 * requests and check the responses (status codes, JSON, etc.).
 */
@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;   // converts objects <-> JSON

    @MockitoBean
    private ProductService productService;   // fake service

    @Test
    void createProduct_returns201() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setName("Mouse");
        request.setUnitPrice(1500.0);
        request.setStock(50);

        ProductResponse response = new ProductResponse(1L, "Mouse", 1500.0, null, "Electronics", 50);
        when(productService.createProduct(any(ProductRequest.class))).thenReturn(response);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productId").value(1))
                .andExpect(jsonPath("$.name").value("Mouse"));
    }

    @Test
    void createProduct_withMissingName_returns400() throws Exception {
        ProductRequest invalid = new ProductRequest();   // no name, no price -> validation fails
        invalid.setUnitPrice(1500.0);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalid)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getProduct_whenExists_returns200() throws Exception {
        ProductResponse response = new ProductResponse(1L, "Mouse", 1500.0, null, "Electronics", 50);
        when(productService.getProductById(1L)).thenReturn(response);

        mockMvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Mouse"));
    }

    @Test
    void getProduct_whenMissing_returns404() throws Exception {
        when(productService.getProductById(99L)).thenThrow(new ProductNotFoundException(99L));

        mockMvc.perform(get("/products/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void deleteProduct_returns204() throws Exception {
        doNothing().when(productService).deleteProduct(1L);

        mockMvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());

        verify(productService).deleteProduct(eq(1L));
    }
}
