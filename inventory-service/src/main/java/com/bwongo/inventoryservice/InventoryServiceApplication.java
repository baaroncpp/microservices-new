package com.bwongo.inventoryservice;

import com.bwongo.inventoryservice.model.Inventory;
import com.bwongo.inventoryservice.repo.InventoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(InventoryRepo inventoryRepo){
		return args -> {
			Inventory inv = new Inventory();
			inv.setSkuCode("qwe123");
			inv.setQuantity(20);

			Inventory inv2 = new Inventory();
			inv.setSkuCode("abc123");
			inv.setQuantity(200);

			inventoryRepo.save(inv2);
			inventoryRepo.save(inv);
		};
	}

}
