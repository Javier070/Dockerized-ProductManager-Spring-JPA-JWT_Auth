package com.jm.tfg.Entidades;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
@Data
@Entity
@Table(name = "user")
//@DynamicInsert//EXPLICA ESTO
//@DynamicUpdate//EXPLICA ESTO
public class User implements Serializable { //puedes borrar la clase serializable ya que @Restcontroller ya se encarga de esto
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
