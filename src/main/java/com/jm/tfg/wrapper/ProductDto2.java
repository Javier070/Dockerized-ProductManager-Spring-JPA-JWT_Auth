package com.jm.tfg.wrapper;

import com.jm.tfg.Entidades.Category;
import com.jm.tfg.Entidades.Product;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Product}
 */
@Value
public class ProductDto2 implements Serializable {
    Long id;
    String name;
    Category category;
    String description;
    Double price;
    String status;
}