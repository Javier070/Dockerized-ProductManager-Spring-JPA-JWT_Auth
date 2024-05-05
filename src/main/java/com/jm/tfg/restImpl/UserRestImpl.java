package com.jm.tfg.restImpl;

import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.rest.UserRest;
import com.jm.tfg.service.UserService;
import com.jm.tfg.utils.TfgUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    private static final Logger logger = LoggerFactory.getLogger(UserRestImpl.class);


    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> registro(Map<String, String> requestMap) {
        try {
            userService.registro(requestMap);
            return ResponseEntity.ok("Registro exitoso");


        } catch (Exception ex) {
            logger.error("Error al registrar usuario", ex); // Utilizamos logger.error para registrar el error
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS, HttpStatus.INTERNAL_SERVER_ERROR);
//        return TfgUtils.getResponseEntity("",HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
//Primer commit

/**
 *     @Override
 *     public ResponseEntity<Object> registro(Map<String, String> requestMap) {
 *         try {
 *             userService.registro(requestMap);
 *             //return ResponseEntity.ok("Registro exitoso");
 *             return ResponseEntity.status(HttpStatus.OK).body(userService.registro(requestMap));
 *
 *         } catch (Exception ex) {
 *             ex.printStackTrace();
 *         }
 *         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TfgConstants.ALGO_SALE_MAL);
 *     }
 * }
 */