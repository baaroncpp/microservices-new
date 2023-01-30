package com.bwongo.productservice.controller;

import com.bwongo.productservice.models.Product;
import com.bwongo.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @Project product-service
 * @Date 1/10/23
 */
@WebMvcTest
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void shouldCreateProduct() throws Exception {
        //Given
        Product given = Product.builder()
                .price(BigDecimal.valueOf(4000))
                .name("iphone")
                .description("New Iphone")
                .build();

        Product result = Product.builder()
                .id(Long.valueOf(1))
                .price(BigDecimal.valueOf(4000))
                .name("iphone")
                .description("New Iphone")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        //Behavior
        when(productService.createProduct(given))
                .thenReturn(result);

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(given)))
                .andDo(print())
                .andExpect(status().isOk());

    }


}