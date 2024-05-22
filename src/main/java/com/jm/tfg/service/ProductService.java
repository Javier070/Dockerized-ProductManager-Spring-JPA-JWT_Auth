package com.jm.tfg.service;

import com.jm.tfg.Entidades.Product;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ProductService {
    ResponseEntity<List<Product>> getAllProducts();

    ResponseEntity<String> addProduct(Map<String, String> requestMap);

    ResponseEntity<String> updateProduct(Map<String, String> requestMap);

    ResponseEntity<String> deleteProduct(Long requestMap);

    ResponseEntity<String> updateStatusProduct(Map<String, String> requestMap);

    ResponseEntity<List<Product>> getProductsByCategory(Long id);

    ResponseEntity<String> getProductById(Long id);
}
