package com.bwongo.productservice.controller;

import com.bwongo.productservice.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Project product-service
 * @Date 1/10/23
 */
//@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductControllerAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapper;

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
            .withDatabaseName("product_db")
            .withUsername("aaron")
            .withPassword("aaron")
            .withExposedPorts(5439);

    /*@DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://localhost:%d/product_db", postgres.getFirstMappedPort()));
        registry.add("spring.datasource.username", () -> "aaron");
        registry.add("spring.datasource.password", () -> "aaron");
    }*/

    @Test
    void shouldCreateProduct() throws Exception {

        //Given
        Product given = Product.builder()
                .price(BigDecimal.valueOf(4000))
                .name("iphone")
                .description("New Iphone")
                .build();

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(given)))
                .andDo(print())
                .andExpect(status().isOk());


    }
}