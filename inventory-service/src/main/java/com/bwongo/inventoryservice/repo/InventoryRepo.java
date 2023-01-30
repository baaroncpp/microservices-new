package com.bwongo.inventoryservice.repo;

import com.bwongo.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project fully_covered
 * @Date 1/16/23
 */
@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findBySkuCode(String code);
    List<Inventory> findBySkuCodeIn(List<String> codes);
}
