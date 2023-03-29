package com.example.Order_Service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private int id;
    private String skuCode;
    private int price;
    private int quantity;

}
