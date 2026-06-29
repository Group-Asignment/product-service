package com.shop.product_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Product entity. Each field below becomes a column in the "product" table.
 * Hibernate reads these annotations and creates/maps the table automatically.
 */
@Entity                          // marks this class as a database entity (a table)
@Table(name = "product")         // table name = "product" (assignment requirement)
@Data                            // Lombok: generates getters, setters, toString, equals
@NoArgsConstructor               // Lombok: generates an empty constructor (JPA needs this)
@AllArgsConstructor              // Lombok: generates a constructor with all fields
public class Product {

    @Id                                                  // primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // DB auto-generates the id (1,2,3...)
    private Long productId;

    private String name;          // product name
    private Double unitPrice;     // price per single unit
    private String description;   // optional extra field (allowed by assignment)
    private String category;      // optional extra field
    private Integer stock;        // optional extra field (quantity available)
}
