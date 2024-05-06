package com.jm.tfg.serviceImpl;

import com.jm.tfg.Entidades.User;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.dao.UserRepository;
import com.jm.tfg.service.UserService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public ResponseEntity<String> registro(Map<String, String> requestMap) {
        log.info("Registro interno MIO{}", requestMap);
        try {



        if (validarRegistroMap(requestMap)){
            User user = userRepository.findByEmail(requestMap.get("email")); //aqui hay dos métodos para el email en el repositorio
            if (Objects.isNull(user)){
                userRepository.save(obtenerUserDeMap(requestMap));
                return TfgUtils.personalizaResponseEntity("Registro Exitoso", HttpStatus.OK);
            }else {
                return TfgUtils.personalizaResponseEntity("Email ya existe",HttpStatus.BAD_REQUEST);
            }
        }else {
//            return  TfgUtils.getResponseEntity(TfgConstants.DATOS_NO_VALIDOS,HttpStatus.BAD_REQUEST); //medio mondongo
            return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS,HttpStatus.BAD_REQUEST);

        }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validarRegistroMap(Map<String, String> requestMap){
        return requestMap.containsKey("name") &&
                requestMap.containsKey("contactNumber") &&
                requestMap.containsKey("email") &&
                requestMap.containsKey("password");

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
