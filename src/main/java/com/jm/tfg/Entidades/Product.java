package com.jm.tfg.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
@JsonPropertyOrder({ "id", "name", "description", "price", "status", "category" }) // reordena los campos

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotBlank(message = "El nombre no puede estar en blanco")
    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @NotBlank(message = "La descripción no puede estar en blanco")
    @Column
    private String description;

    @Column
    private Double price;

    @Column(nullable = false)
    @NotNull
    private  String status;

}
