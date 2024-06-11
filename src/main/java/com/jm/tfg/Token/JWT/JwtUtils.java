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


    public Claims extraerTodosReclamos(String token){
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public <T> T extraerReclamo(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extraerTodosReclamos(token);
        return  claimsResolver.apply(claims);
    }


    public String extraerUsuarioNombre(String token){
        return extraerReclamo(token, claims -> claims.getSubject());
    }




////////////////////////////////






//aqui le agregammos los claims
    public String crearToken(Map<String, Object> claims, String username) {
        try {
            return Jwts
                    .builder() //construir para luego setear todos los campo
                    .setClaims(claims)
                    .setSubject(username) // identificador del propietario del token. En este caso es el username.
                    .setIssuedAt(new Date(System.currentTimeMillis())) //emision del token
                    .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h, 10 horas //caducidad del token
                    .signWith(SignatureAlgorithm.HS256, secret) // frimar el token con el alogoritmo y la firma
                    .compact(); //compacta el token en un cadena
        } catch (Exception ex) {
            // Manejar la excepción y registrarla
            log.error("Error al crear el token JWT", ex);
            // Lanzar excepción personalizada
            throw new RuntimeException("Error al crear el token JWT", ex);

        }
    }
    //este método es llamado  en el login y luego se usara para el metodo de arriba
    public String generarToken(String username,String role){
        Map <String, Object> claims = new HashMap<>();
        claims.put("role",role);
        return crearToken(claims,username); //pasamos el usuario y el rol  a generar token
    }
    /////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////

    public Date extraerCaducidad(String token){
        return extraerReclamo(token,claims-> claims.getExpiration());
    }

    public Boolean tokenCaducado(String token){
        return  extraerCaducidad(token).before(new Date());
    }


    public Boolean validaToken(String token, UserDetails userDetails){
        final String username = extraerUsuarioNombre(token);
        return (username.equals(userDetails.getUsername()) && !tokenCaducado(token));
    }


}

