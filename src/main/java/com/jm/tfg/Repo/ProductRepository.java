package com.jm.tfg.Repo;

import com.jm.tfg.Entidades.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}