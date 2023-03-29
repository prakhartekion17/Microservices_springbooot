package com.example.Product_Service.repository;

import com.example.Product_Service.model.Product;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.mongodb.repository.MongoRepository;

@ReadingConverter
public interface ProductRepository extends MongoRepository<Product,String> {

}
