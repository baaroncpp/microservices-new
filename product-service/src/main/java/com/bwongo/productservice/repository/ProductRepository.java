package com.bwongo.productservice.repository;

import com.bwongo.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Project product-service
 * @Date 1/4/23
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
