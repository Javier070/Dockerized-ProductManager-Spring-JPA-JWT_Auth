package com.jm.tfg.Seguridad.JWT;

import com.jm.tfg.Seguridad.CustomerDetailsService;
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
        if (request.getServletPath().matches("/user/login|user/forgotPassword|/user/registro|/user/verifyToken")){  //todo puede ser que tengas que cambiar el nombre a los métodos
          filterChain.doFilter(request, response);
        }
        else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null; // se inicializa el token

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){ // 7 caracteresj
                token = authorizationHeader.substring(7);
                username = jwtUtils.extraerUsuarioNombre(token);
                claims = jwtUtils.extraerTodosReclamos(token);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
                if (jwtUtils.validaToken(token,userDetails)){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                            new WebAuthenticationDetailsSource().buildDetails(request);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);


                }
            }
            filterChain.doFilter(request, response);

        }
    }
    public Boolean isAdmin(){
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public Boolean isUser(){
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }


    public String getCurrentUser(){
        return username;
    }
}

