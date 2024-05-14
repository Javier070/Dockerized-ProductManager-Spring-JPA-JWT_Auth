package com.jm.tfg.Token.JWT;

import com.jm.tfg.Token.CustomerDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
                    logger.error("Error al extraer usuario y reclamos en el metodo doFilterInternal: " + e.getMessage());
                }
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
                    logger.error("Error durante la validación del token JWT y la autenticación del usuario  en el metodo doFilterInternal: " + e.getMessage());
                }
            }
            filterChain.doFilter(request, response);

        }
    }

    public boolean isAdmin() {
        return "admin".equalsIgnoreCase(String.valueOf(claims.get("role")));
    }


    public boolean isUser() {
        return "user".equalsIgnoreCase(String.valueOf(claims.get("role")));
    }

    public String getCurrentUser() {
        return username;
    }
}
