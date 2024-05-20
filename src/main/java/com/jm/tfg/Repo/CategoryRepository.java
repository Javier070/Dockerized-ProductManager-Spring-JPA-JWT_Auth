package com.jm.tfg.Repo;

import com.jm.tfg.Entidades.Category;
import com.jm.tfg.Entidades.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.id in (SELECT p.category.id FROM Product p WHERE p.status='true') ")
    List<Category> findCategoriesWithActiveProducts();



}