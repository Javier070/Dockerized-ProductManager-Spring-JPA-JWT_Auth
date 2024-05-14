package com.jm.tfg.dao;

import com.jm.tfg.Entidades.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
//    List<Category> getAllCategories();

    List<Category> findAll();

}