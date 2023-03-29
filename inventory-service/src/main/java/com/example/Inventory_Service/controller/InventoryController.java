package com.example.Inventory_Service.controller;

import com.example.Inventory_Service.dto.InventoryResponse;
import com.example.Inventory_Service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired

    private InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List< String> skuCode)
    {
        return inventoryService.isInStock(skuCode);
    }
}
