package com.jm.tfg.Entidades;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product")
@JsonPropertyOrder({ "id", "name", "description", "price", "status", "category" })

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)// he cambiado el fetch a eager para que se cargue la categoria
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    private String description;

    @Column
    private Double price;

    @Column(nullable = false)
    @NotNull
    private  String status;

}
