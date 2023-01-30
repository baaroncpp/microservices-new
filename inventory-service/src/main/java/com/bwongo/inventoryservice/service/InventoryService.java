package com.bwongo.inventoryservice.service;

import com.bwongo.inventoryservice.model.Inventory;

import java.util.List;

/**
 * @Author bkaaron
 * @Project fully_covered
 * @Date 1/16/23
 */
public interface InventoryService {
    List<Inventory> isInStock(List<String> skuCode);
}
