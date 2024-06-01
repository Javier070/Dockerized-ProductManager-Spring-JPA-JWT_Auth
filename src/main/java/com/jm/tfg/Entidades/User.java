package com.jm.tfg.Entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NamedQuery(name = "User.findByEmail", query = "select u from User u where u.email=:email")
//TODO mejora esta clase con @
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotBlank(message = "El nombre no puede estar en blanco")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Pattern(regexp = "^\\+?[0-9 -]{7,12}$", message = "El número de contacto tiene un formato inválido")
    @Column(name = "contact_number", length = 25)
    private String contactNumber;



    @Email(message = "Debe ser una dirección de correo electrónico válida")
    @NotBlank(message = "El correo electrónico no puede estar en blanco")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull
    @Column
    private String status;

    @NotNull
    @Column
    private String role;


}
