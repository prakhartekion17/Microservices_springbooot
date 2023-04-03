package com.example.Order_Service.service;

import com.example.Order_Service.dto.InventoryResponse;
import com.example.Order_Service.dto.OrderRequest;
import com.example.Order_Service.event.OrderPlacedEvent;
import com.example.Order_Service.model.Order;
import com.example.Order_Service.model.OrderLineItems;
import com.example.Order_Service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;

    private KafkaTemplate <String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest)
    {
        Order order= new Order();
        order.setOrderNumber(UUID.randomUUID().toString());


        List<OrderLineItems> orderLineItems1 = orderRequest.getOrderLineItemsDtoList().stream().map(
                orderLineItemsDto -> {
                    OrderLineItems orderLineItems = new OrderLineItems();
                    orderLineItems.setPrice(orderLineItemsDto.getPrice());
                    orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
                    orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
                    return orderLineItems;

                }
        ).collect(Collectors.toList());
        order.setOrderLineItemsList(orderLineItems1);

        List<String> skuCodes=order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode
        ).collect(Collectors.toList());

        //call inventory service to check for availability and the make the order
        InventoryResponse[] inventoryResponseArray=webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory", uriBuilder ->uriBuilder.queryParam("skuCode",skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        log.info("now here");
//        log.info(order.getOrderNumber());
//        assert inventoryResponseArray != null;
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
        log.info(String.valueOf(allProductsInStock==true));
        if(allProductsInStock) {
            orderRepository.save(order);
            log.info("Order number: "+order.getOrderNumber());
//
            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(order.getOrderNumber()));
        }
        else {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
    }
}
