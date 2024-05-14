package com.jm.tfg.Token.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Service
public class JwtUtils {
    //todo repasar lambda
    private final String secret = "buenosdias";


    public <T> T extraerReclamo(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extraerTodosReclamos(token);
        return  claimsResolver.apply(claims);
    }

    public String extraerUsuarioNombre(String token){
        return extraerReclamo(token, claims -> claims.getSubject());
    }

    public Date extraerCaducidad(String token){
        return extraerReclamo(token,claims-> claims.getExpiration());
    }
    public Boolean tokenCaducado(String token){
        return  extraerCaducidad(token).before(new Date());
    }

////////////////////////////////
    public Claims extraerTodosReclamos(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
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
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
                    .signWith(SignatureAlgorithm.HS256, secret)
                    .compact();
        } catch (Exception ex) {
            // Manejar la excepción y registrarla
            log.error("Error al crear el token JWT", ex);
            // Lanzar excepción personalizada
            throw new RuntimeException("Error al crear el token JWT", ex);

        }
    }


    public Boolean validaToken(String token, UserDetails userDetails){
        final String username = extraerUsuarioNombre(token);
        return (username.equals(userDetails.getUsername()) && tokenCaducado(token));
    }


}

