package com.bwongo.productservice.service;

import com.bwongo.productservice.models.Product;
import com.bwongo.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @Project product-service
 * @Date 1/10/23
 */
@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductServiceImpIntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Container
    PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
            .withDatabaseName("product_db")
            .withUsername("aaron")
            .withPassword("aaron");

    @Test
    void createProduct() {

        Product given = Product.builder()
                .price(BigDecimal.valueOf(4000))
                .name("iphone")
                .description("New Iphone")
                .build();

        Product actualProduct = productRepository.save(given);

        assertThat(actualProduct).usingRecursiveComparison()
                .ignoringFields("id").isEqualTo(given);
    }
}