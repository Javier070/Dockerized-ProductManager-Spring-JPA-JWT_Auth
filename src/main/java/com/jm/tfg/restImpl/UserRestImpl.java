package com.jm.tfg.restImpl;

import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.rest.UserRest;
import com.jm.tfg.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    @Autowired
    UserService userService;
    @Override
    public ResponseEntity<String> registro(Map<String, String> requestMap) {
        try {
            userService.registro(requestMap);
            return ResponseEntity.ok("Registro exitoso");


        } catch (Exception ex) {
            ex.   printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TfgConstants.ALGO_SALE_MAL);

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
 *
 *         } catch (Exception ex) {
 *             ex.printStackTrace();
 *         }
 *         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(TfgConstants.ALGO_SALE_MAL);
 *
 *     }
 * }
 */