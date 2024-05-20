package com.jm.tfg.Repo;

import com.jm.tfg.Entidades.Category;
import com.jm.tfg.Entidades.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {



}