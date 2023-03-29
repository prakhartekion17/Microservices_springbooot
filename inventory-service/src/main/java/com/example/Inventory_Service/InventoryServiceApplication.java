package com.example.Inventory_Service;

import com.example.Inventory_Service.model.Inventory;
import com.example.Inventory_Service.repository.InventoryRepository;
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
	public CommandLineRunner loadData(InventoryRepository inventoryRepository)
	{
		return args->{
			Inventory inventory= new Inventory();
			inventory.setSkuCode("Iphone_13");
			inventory.setQuantity(100);
			Inventory inventory1= new Inventory();
			inventory1.setSkuCode("Iphone_13_red");
			inventory1.setQuantity(0);
			inventoryRepository.save(inventory);
			inventoryRepository.save(inventory1);
		};
	}
}