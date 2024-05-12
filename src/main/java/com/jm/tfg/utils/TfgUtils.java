package com.jm.tfg.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class TfgUtils {
    private  TfgUtils(){

    }
    public  static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus){ //los parámetros son un mensaje de respuesta personalizado y el httpStatus
            return  new ResponseEntity<String>("{\"message\":\""+responseMessage+"\"}",httpStatus);
    }

    public static ResponseEntity<String> personalizaResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body("{\"mensaje\":\"" + responseMessage + "\"}");
    }


}
