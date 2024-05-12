package com.jm.tfg.Entidades;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email=:email")
//TODO mejora esta clase con @
@Data
@Entity
@Table(name = "user")
@DynamicInsert// genera sentencias omitiendo campos con valores nulos, mejorando la eficiencia en la inserción de datos.
@DynamicUpdate//genera sentencias de actualización omitiendo campos que no han cambiado, optimizando la eficiencia en operaciones de actualización en la base de datos.
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column
    private String name;
    private String contactNumber;
    private String email;
    private String password;
    private String status;
    private String role;


}
