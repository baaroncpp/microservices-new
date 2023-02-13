package com.bwongo.inventoryservice.service;

import com.bwongo.inventoryservice.model.Inventory;
import com.bwongo.inventoryservice.repo.InventoryRepo;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author bkaaron
 * @Project fully_covered
 * @Date 1/16/23
 */
@Service
@AllArgsConstructor
@Slf4j
public class InventoryServiceImp implements InventoryService{

    private InventoryRepo inventoryRepo;

    @Override
    @SneakyThrows //do use in production
    public List<Inventory> isInStock(List<String> skuCodes) {
        log.info("Start wait");
        //Thread.sleep(30000);
        log.info("End wait");

        List<Inventory> stock = inventoryRepo.findBySkuCodeIn(skuCodes);
        log.info("Stock checked");
        return stock;
    }
}
