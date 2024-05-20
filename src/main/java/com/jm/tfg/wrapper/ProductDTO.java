package com.jm.tfg.wrapper;


import com.jm.tfg.Entidades.Product;
import lombok.Data;



@Data
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String status;
    private CategoryDto category;

    public ProductDTO() {}

    public ProductDTO(Product product) {
        this.id = product.getId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.status = product.getStatus();
        this.category = new CategoryDto(product.getCategory().getId(), product.getCategory().getName());


    }

    // Getters and setters...
}
