package com.bwongo.productservice.service;

import com.bwongo.productservice.models.Product;

import java.util.List;

/**
 * @Project product-service
 * @Date 1/4/23
 */
public interface ProductService {
    Product createProduct(Product product);
    List<Product> getAllProducts();
}
