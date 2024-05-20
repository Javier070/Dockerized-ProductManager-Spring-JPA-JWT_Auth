package com.jm.tfg.service;


import com.jm.tfg.Entidades.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CategoryService{

    public ResponseEntity<String> agregarNuevaCategoria(Map<String, String> requestMap);


    ResponseEntity<String> updateCategory(Map<String, String> requestMap);

    ResponseEntity<String> deleteCategory(Long id);

    ResponseEntity<List<Category>> getAllCategoria();

    ResponseEntity<List<Category>> getAllCategoria2(String filterValue);
}
