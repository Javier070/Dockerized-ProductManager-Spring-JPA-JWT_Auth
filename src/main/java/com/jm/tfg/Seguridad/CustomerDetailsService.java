package com.jm.tfg.Seguridad;

import com.jm.tfg.Entidades.User;
import com.jm.tfg.dao.UserDAO;
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

    @Autowired
    private UserDAO userDAO;

private User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Esto es loadUserByUsername{}", username);
        userDetail = userDAO.findByEmail(username);
        if (!Objects.isNull(username)) {
            return new org.springframework.security.core.userdetails.User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>()); //El User de spring security necesitas dos parametros name y password, en este caso el gmail sera el name
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado");
        }
    }

    public User getUserDetail(){
        return userDetail;
    }
}

