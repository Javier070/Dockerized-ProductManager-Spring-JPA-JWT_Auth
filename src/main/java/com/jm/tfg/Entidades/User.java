package com.jm.tfg.Entidades;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serial;
import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")
//TODO mejora esta clase con @
@Data
@Entity
@Table(name = "user")
@DynamicInsert//EXPLICA ESTO
@DynamicUpdate//EXPLICA ESTO
public class User implements Serializable { //puedes borrar la clase serializable ya que @Restcontroller ya se encarga de esto
    @Serial
    private  static  final  long serialVersionUID = 1L;

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
