package com.bwongo.inventoryservice.controller;

import com.bwongo.inventoryservice.model.Inventory;
import com.bwongo.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author bkaaron
 * @Project microservices-new
 * @Date 1/16/23
 */
@RestController
@RequestMapping("api/v1/inventory")
@AllArgsConstructor
public class InventoryApi {

    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Inventory> isInStock(@RequestParam List<String> skuCodes){
        return inventoryService.isInStock(skuCodes);
//                .stream()
//                .filter(s -> s.getQuantity() > 0).collect(Collectors.toList());
    }

}
