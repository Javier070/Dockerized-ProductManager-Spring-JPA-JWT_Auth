package com.jm.tfg.service;

import com.jm.tfg.Entidades.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> registro(Map<String, String> requestMap);

    ResponseEntity <String> login(Map<String, String> requestMap);

   ResponseEntity<List<User>> getAllUsers();
}
