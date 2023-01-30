package com.bwongo.productservice;

import com.bwongo.productservice.models.Product;
import com.bwongo.productservice.repository.ProductRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class ProductServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:11")
			.withDatabaseName("product_db")
			.withUsername("aaron")
			.withPassword("aaron")
			.withExposedPorts(5432);

	@Autowired
	private ProductRepository productRepository;

	/*@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry){
		registry.add("spring.datasource.url", () -> String.format("jdbc:postgresql://localhost:%d/product_db", postgres.getFirstMappedPort()));
		registry.add("spring.datasource.username", () -> "aaron");
		registry.add("spring.datasource.password", () -> "aaron");
	}*/

	@Test
	void shouldCreateProduct() throws Exception {
		String productString = objectMapper.writeValueAsString(getProductRequest());
		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/product")
				.contentType(MediaType.APPLICATION_JSON)
				.content(productString))
				.andExpect(status().isOk());

		Assertions.assertEquals(1, productRepository.findAll().size());
	}

	private Product getProductRequest(){
		return Product.builder()
				.name("Galaxy S8")
				.description("Samsung product")
				.price(BigDecimal.valueOf(12000))
				.build();
	}

}
