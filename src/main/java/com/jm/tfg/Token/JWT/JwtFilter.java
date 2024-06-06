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
/**
 * Esta clase es un filtro que intercepta las solicitudes HTTP entrantes y procesa los tokens JWT.
 * Extiende OncePerRequestFilter para asegurar que el filtro se ejecute solo una vez por solicitud.
 */
@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    /**
     * La instancia de JwtUtils utilizada para la manipulación de tokens.
     */
    @Autowired
    private JwtUtils tokenJWTGenerator;

    /**
     * La instancia de CustomerDetailsService utilizada para cargar los detalles del usuario.
     */
    @Autowired
    CustomerDetailsService customerDetailsService;


    private String username = null;

    private Claims claims = null;

    //filtro de seguridad donde la peticion llegará antes que a los controllers, realizara una verificación del token


    /**
     * Este método se llama para cada solicitud HTTP entrante.
     * Verifica si la ruta de la solicitud coincide con las rutas para las cuales no se requiere autenticación JWT.
     * Si la ruta de la solicitud coincide, la cadena de filtros continúa.
     * -------------------------------------------------------------------------
     * Si la ruta de la solicitud no coincide, se extrae el token JWT del encabezado de la solicitud.
     * Luego se valida el token y se cargan los detalles del usuario.
     * Si el token es válido, se crea un UsernamePasswordAuthenticationToken y se establece en el contexto de seguridad.
     * Si ocurre alguna excepción durante el procesamiento, se registran los mensajes de error correspondientes.
     *
     *
     * @param request La solicitud HTTP entrante.
     *
     * @param response La respuesta HTTP.
     *
     * @param filterChain La cadena de filtros.
     *
     * @throws ServletException Si ocurre un error durante el procesamiento de la solicitud.
     * @throws IOException Si ocurre un error durante el procesamiento de la solicitud.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().matches("/user/login|/user/forgotPassword|/user/registro|")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null; // se inicializa el token

            // Extraer el token del encabezado de autorización
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) { // 7 caracteres
                token = authorizationHeader.substring(7);
                try {
                    // Extraer el nombre de usuario y los claims del token
                    username = tokenJWTGenerator.extraerUsuarioNombre(token);
                    claims = tokenJWTGenerator.extraerTodosReclamos(token);
                } catch (Exception e) {
                    // Registrar la excepción
                    log.error("Error al extraer el nombre de usuario y los claims del token en el método doFilterInternal: " + e.getMessage());
                }
            }
            else {
                log.error("authorizationHeader es null");
            }

            // Validar el token y autenticar al usuario
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                try {
                    // Cargar los detalles del usuario
                    UserDetails userDetails = customerDetailsService.loadUserByUsername(username);
                    // Validar el token
                    if (tokenJWTGenerator.validaToken(token, userDetails)) {
                        // Crear un UsernamePasswordAuthenticationToken y establecerlo en el contexto de seguridad
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        new WebAuthenticationDetailsSource().buildDetails(request);
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (Exception e) {
                    // Registrar la excepción
                    log.error("Error durante la validación del token JWT y la autenticación del usuario en el método doFilterInternal: " + e.getMessage());
                }
            } else {
                if (username == null) {
                    log.error("El nombre de usuario extraído del token es null. Verifica si el token es correcto.");
                } else if (SecurityContextHolder.getContext().getAuthentication() != null) {
                    log.error("El contexto de seguridad ya contiene una autenticación. Posible intento de sobrescribir una autenticación existente.");
                }
            }
            // Continuar con la cadena de filtros
            filterChain.doFilter(request, response);
        }
    }

    /**
     * Este método verifica si el usuario actual tiene el rol de administrador.
     *
     * @return True si el usuario actual tiene el rol de administrador, false de lo contrario.
     */
    public boolean isAdmin() {
        if (claims == null) {
            log.error("Claims es null en el método isAdmin. Asegúrate de que el token JWT es válido y ha sido procesado correctamente.");
            return false;
        }
        return "admin".equalsIgnoreCase(String.valueOf(claims.get("role")));
    }

    /**
     * Este método verifica si el usuario actual tiene el rol de usuario.
     *
     * @return True si el usuario actual tiene el rol de usuario, false de lo contrario.
     */
    public boolean isUser() {
        if (claims == null) {
            log.error("Claims es null en el método isUser. Asegúrate de que el token JWT es válido y ha sido procesado correctamente.");
            return false;
        }
        return "user".equalsIgnoreCase(String.valueOf(claims.get("role")));
    }

    /**
     * Este método devuelve el nombre de usuario del usuario actual.
     *
     * @return El nombre de usuario del usuario actual.
     */
    public String getCurrentUser() {
        return username;
    }


}

