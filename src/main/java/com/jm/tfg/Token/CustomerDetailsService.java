package com.jm.tfg.Token;

import com.jm.tfg.Entidades.User;
import com.jm.tfg.Repo.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@Slf4j
public class CustomerDetailsService implements UserDetailsService {

    // user details es la representación de un User en Spring
    //Esta clase se encarga de traer el user desde DB con el f



    //**
    // UserDetails es una interfaz que permite a Spring Security trabajar con diferentes tipos de usuarios
    // y obtener los detalles necesarios para realizar la autenticación y la autorización.
    //
    //*

    @Autowired
    private UserDAO userDAO;

private User userDetail;
    /**
     /**
     * Este método es utilizado por Spring Security para cargar los detalles del usuario desde la base de datos.
     * Recupera los detalles del usuario basándose en el nombre de usuario proporcionado (en este caso, el correo electrónico).
     *
     * @param username El nombre de usuario (correo electrónico) del usuario que se va a cargar.
     * @return UserDetails: Los detalles del usuario cargado.
     * @throws UsernameNotFoundException Si no se encuentra al usuario con el nombre de usuario dado.
     */

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Esto es loadUserByUsername{}", username);

        // Recupera el Mail del usuario desde la base de datos usando el UserDAO
         userDetail = userDAO.findByEmail(username);

        // Verifica si el usuario existe
        if (userDetail != null) {
            // Crea un objeto User de Spring Security con los detalles del usuario recuperado
            // El objeto User requiere dos parámetros: nombre de usuario (correo electrónico) y contraseña
            // En este caso, se usa el correo electrónico como nombre de usuario
            return new org.springframework.security.core.userdetails.User(
                    userDetail.getEmail(),
                    userDetail.getPassword(),
                    new ArrayList<>()); //lo mandamos vacio porque mandar null puede generar problemas
        } else {
            // Si no se encuentra al usuario, lanza una UsernameNotFoundException
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }


    /**
     * Este método se utiliza en el LOGIN para recuperar un objeto user details  de la base de datos.
     *
     * @return User: el objeto  user detail obtenido de la base de datos.
     */
    public User getUserDetail(){
        return userDetail;
    }
}

