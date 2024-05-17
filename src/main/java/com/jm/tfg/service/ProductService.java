package com.jm.tfg.service;

import com.jm.tfg.Entidades.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<String> addProduct(Map<String, String> requestMap);
}
