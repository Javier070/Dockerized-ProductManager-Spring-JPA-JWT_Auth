package com.jm.tfg.rest;

import com.jm.tfg.Entidades.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping( "/user")
public interface UserRest {
    @PostMapping("/registro")
    public ResponseEntity<String> registro (@RequestBody ()Map<String, String> requestMap);
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody ()Map<String, String> requestMap);
    @PostMapping("/verifyToken")
    ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String authorizationHeader);

    @GetMapping("/getAll")
    ResponseEntity<List<User>> getAllUsers();

    @PostMapping("/updateStatus")
    ResponseEntity<String> updateUserStatus(@RequestBody Map<String, String> requestMap);





}
//public ResponseEntity<String> registro(@RequestBody(required = true) DatosRegistro datosRegistro);
