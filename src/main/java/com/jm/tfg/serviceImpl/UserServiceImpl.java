package com.jm.tfg.serviceImpl;

import com.jm.tfg.Entidades.Category;
import com.jm.tfg.Entidades.User;
import com.jm.tfg.Token.CustomerDetailsService;
import com.jm.tfg.Token.JWT.JwtFilter;
import com.jm.tfg.Token.JWT.JwtUtils;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.Repo.UserDAO;
import com.jm.tfg.service.UserService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private JwtFilter jwtFilter;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomerDetailsService customerDetailsService;

    @Autowired
    private JwtUtils jwtUtils;
//  ESTE ES MI LOGIN, VOY A PROBAR CON OTRO
//    @Override
//    public ResponseEntity<String> login(Map<String, String> requestMap) {
//        log.info("Dentro de login");
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password")));
//            if (authentication.isAuthenticated()){
//                if(customerDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")){
//                    return new ResponseEntity<String>(
//                            ("{\"token\": \"" +
//                                    jwtUtils.generarToken(customerDetailsService.getUserDetail().getEmail(),
//
//                                            customerDetailsService.getUserDetail().getRole()) + "\"}"),
//                            HttpStatus.OK);
//                }
//                else{
//                    return new ResponseEntity<String>("{\"message\": \""+ "Espera para ser aceptado por del administrador "+"\"}", HttpStatus.BAD_REQUEST);
//                }
//            }
//        }catch (Exception ex){
//            log.error("{}", ex);
//        }
//        return new ResponseEntity<String>("{\"message\": \""+ "Credenciales Incorrectas"+"\"}", HttpStatus.BAD_REQUEST);
//    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Dentro de login");
        try {
            // Intenta autenticar al usuario utilizando el AuthenticationManager y las credenciales proporcionadas
            Authentication authentication =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            requestMap.get("email"),
                            requestMap.get("password")));
            // Verifica si la autenticación fue exitosa
            if (authentication.isAuthenticated()) {
                // Obtiene los detalles del usuario autenticado
                User user = customerDetailsService.getUserDetail();
                // Verifica si el usuario está activo (status = true) en DB)
                if (user.getStatus().equalsIgnoreCase("true")) {
                    // Genera un token JWT para el usuario autenticado
                    String token = jwtUtils.generarToken(user.getEmail(), user.getRole());
                    // Retorna una respuesta exitosa con el token JWT en el cuerpo
                    return ResponseEntity.ok("{\"token\": \"" + token + "\"}");
                } else {
                    //el usuario es (status = true) en DB, necesita ser aprobado por el administrador

                    return ResponseEntity.badRequest().body("{\"mensaje\": \"Espera para ser aceptado por el administrador\"}");
                }
            }
        } catch (Exception ex) {
            log.error("Error en la autenticación", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS,HttpStatus.BAD_REQUEST);

    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> arrayError = new ArrayList<>();// Cuando la respuesta des de error, se devuelve un array vacio
        List<User> usuarios = userDAO.findAll();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(usuarios);
        } catch (Exception ex) {
            log.error("Error al obtener todas las categorias desde el service", ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(arrayError);
    }

    @Override
    public ResponseEntity<String> updateUserStatus(Map<String, String> requestMap) {
        log.info("Actualizando el estatus del usuario");
        try {
            if (jwtFilter.isAdmin() && requestMap.containsKey("id") && requestMap.containsKey("status")) {
                long userId = Long.parseLong(requestMap.get("id"));
                String newStatus = requestMap.get("status");

                // Actualiza el estado del usuario en la base de datos
                userDAO.updateStatus(newStatus, userId);
                return TfgUtils.personalizaResponseEntity(TfgConstants.OPERACION_EXITOSA, HttpStatus.OK);
            } else {
                return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            log.error("Error al actualizar el estatus del usuario", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }




    @Override
    public ResponseEntity<String> registro(Map<String, String> requestMap) {
        log.info("Registro interno de un usuario{}", requestMap);// quiero ver impreso ese Map
        try {
        if (validarRegistroMap(requestMap)){
            User user = userDAO.findByEmail(requestMap.get("email")); //aqui hay dos métodos para el email en el repositorio
            if (user == null){
                userDAO.save(obtenerUserDeMap(requestMap));
                return TfgUtils.personalizaResponseEntity("Registro Exitoso", HttpStatus.CREATED);
            }else {

                return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS,HttpStatus.BAD_REQUEST);

            }
        }else {
//            return  TfgUtils.getResponseEntity(TfgConstants.DATOS_NO_VALIDOS,HttpStatus.BAD_REQUEST); //medio mondongo

            log.warn("El usuario ya existe");
            return TfgUtils.personalizaResponseEntity("Email ya existe",HttpStatus.BAD_REQUEST);

        }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validarRegistroMap(Map<String, String> requestMap){
        if (requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("password")){
        return  true;
        }
        return false;
    }

    private User obtenerUserDeMap(Map<String, String> requestMap){
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }


}
