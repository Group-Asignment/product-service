package com.shop.product_service.repository;

import com.shop.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The repository talks to the database for us.
 * By extending JpaRepository, Spring AUTOMATICALLY gives us methods like:
 *   - save(product)        -> INSERT or UPDATE
 *   - findById(id)         -> SELECT by primary key
 *   - deleteById(id)       -> DELETE by primary key
 *   - findAll()            -> SELECT all rows
 * We don't write any SQL ourselves.
 *
 * <Product, Long> means: this repository manages Product entities
 * whose primary key (id) is of type Long.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // No code needed here for now — Spring provides all the basic methods.
}
