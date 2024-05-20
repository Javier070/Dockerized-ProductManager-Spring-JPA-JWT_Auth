package com.jm.tfg.Token.JWT;

import com.jm.tfg.Token.CustomerDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    CustomerDetailsService customerDetailsService;

    private String username = null;

    Claims claims = null;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().matches("/user/login|/user/forgotPassword|/user/registro|")) {
            filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null; // se inicializa el token


            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){ // 7 caracteres
                token = authorizationHeader.substring(7);
                try {
                    username = jwtUtils.extraerUsuarioNombre(token);
                    claims = jwtUtils.extraerTodosReclamos(token);
                } catch (Exception e) {
                    // Registrar la excepción
                    log.error("Error al extraer el nombre de usuario y los claims del token en el método doFilterInternal: " + e.getMessage());
                }
            }

            else{
               log.error("authorizationHeader es null");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                try {
                UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
                if (jwtUtils.validaToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                } catch (Exception e) {
                    // Registrar la excepción
                    log.error("Error durante la validación del token JWT y la autenticación del usuario  en el metodo doFilterInternal: " + e.getMessage());
                }
            } else {
                if (username == null) {
                    log.error("El nombre de usuario extraído del token es null. Verifica si el token es correcto.");
                } else if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    log.error("El contexto de seguridad ya contiene una autenticación. Posible intento de sobrescribir una autenticación existente.");
                }
            }
            filterChain.doFilter(request, response);

        }
    }

    public boolean isAdmin() {
        if (claims == null) {
            log.error("Claims es null en el método isAdmin. Asegúrate de que el token JWT es válido y ha sido procesado correctamente.");
            return false;
        }
        return "admin".equalsIgnoreCase(String.valueOf(claims.get("role")));
    }


    public boolean isUser() {
        if (claims == null) {
            log.error("Claims es null en el método isUser. Asegúrate de que el token JWT es válido y ha sido procesado correctamente.");
            return false;
        }
        return "user".equalsIgnoreCase(String.valueOf(claims.get("role")));
    }

    public String getCurrentUser() {
        return username;
    }
}
