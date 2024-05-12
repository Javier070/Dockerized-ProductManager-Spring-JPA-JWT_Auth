package com.jm.tfg.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {
    @PostMapping("/registro")
    public ResponseEntity<String> registro (@RequestBody (required = true)Map<String, String> requestMap);
    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody (required = true)Map<String, String> requestMap);
    @PostMapping("/verifyToken")
    ResponseEntity<String> verifyToken(@RequestHeader("Authorization") String authorizationHeader);



}
//public ResponseEntity<String> registro(@RequestBody(required = true) DatosRegistro datosRegistro);
