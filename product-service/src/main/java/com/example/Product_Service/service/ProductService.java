package com.example.Product_Service.service;

import com.example.Product_Service.dto.ProductRequest;
import com.example.Product_Service.dto.ProductResponse;
import com.example.Product_Service.model.Product;
import com.example.Product_Service.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductService {

    @Autowired
    private ProductRepository productRepository;


    public void createProduct(ProductRequest productRequest)
    {
        Product product= Product.builder().name(productRequest.getName()).desc(productRequest.getDesc()).price(productRequest.getPrice()).build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }
    public List<ProductResponse> getAllProducts()
    {
        List<Product> products= productRepository.findAll();
        return products.stream().map(product -> {
            return ProductResponse.builder().id(product.getId()).price(product.getPrice()).name(product.getName()).desc(product.getDesc()).build();
        }).collect(Collectors.toList());
    }
}
