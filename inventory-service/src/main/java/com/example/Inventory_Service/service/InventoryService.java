package com.example.Inventory_Service.service;

import com.example.Inventory_Service.dto.InventoryResponse;
import com.example.Inventory_Service.repository.InventoryRepository;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Transactional(readOnly = true)
//    @SneakyThrows
    public List<InventoryResponse> isInStock(List<String> skuCode)
    {
//        log.info("Wait Started");
//        Thread.sleep(10000);
//        log.info("Wait Ended");
//        return true;
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory -> {
                    return InventoryResponse.builder().skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0).build();
                }).collect(Collectors.toList());

    }

}
