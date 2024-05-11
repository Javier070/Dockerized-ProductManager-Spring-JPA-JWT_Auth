package com.jm.tfg.Seguridad.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtils {

    private String secret = "buenosdias";

    public String extraerUsuarioNombre(String token){
        return extraerReclamo(token,Claims::getSubject);
    }

    public Date extraerCaducidad(String token){
        return extraerReclamo(token,Claims::getExpiration);
    }

    public <T> T extraerReclamo(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extraerTodosReclamos(token);
        return  claimsResolver.apply(claims);
    }

    public Claims extraerTodosReclamos(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public Boolean tokenCaducado(String token){
        return  extraerCaducidad(token).before(new Date());
    }


    public String generarToken(String username,String role){
        Map <String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return crearToken(claims,username);
    }

    public String crearToken(Map<String, Object> claims, String subject) {
        try {
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 horas
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (Throwable th) {
            // Manejar la excepción y registrarla
            System.err.println("Error al crear el token JWT: " + th.getMessage());
            th.printStackTrace();
            //  lanzar  excepción personalizada
            throw new RuntimeException("Error al crear el token JWT", th);
        }
    }


    public Boolean ken(String token, UserDetails userDetails){
        final String username = extraerUsuarioNombre(token);
        return (username.equals(userDetails.getUsername()) && tokenCaducado(token));
    }


}

