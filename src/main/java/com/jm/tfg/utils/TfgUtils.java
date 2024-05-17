package com.jm.tfg.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class TfgUtils {
    private  TfgUtils(){

    }
    public static ResponseEntity<String> personalizaResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body("{\"mensaje\":\"" + responseMessage + "\"}");
    }
//    int lanzaCatch = 10 / 0;

//   métodos descontinuados
//    public static <T> ResponseEntity<List<T>> personalizaResponseEntityList6(List<T> dataList, HttpStatus httpStatus) {
//        return ResponseEntity.status(httpStatus).body(dataList);
//    }
//
//
//    public static ResponseEntity<List<String>> personalizaResponseEntityList5(String responseMessage, HttpStatus httpStatus) {
//        List<String> emptyList = new ArrayList<>();
//        emptyList.add(responseMessage);
//        return ResponseEntity.status(httpStatus).body(emptyList);
//    }

//
//    public static ResponseEntity<List<String>> personalizaResponseEntityList4(String responseMessage, HttpStatus httpStatus) {
//
//        return ResponseEntity.status(httpStatus).body(responseMessage + new ArrayList<>());
//    }
//
//
//    public static ResponseEntity<List<>> personalizaResponseEntityList(String responseMessage, HttpStatus httpStatus) {
//        return ResponseEntity.status(httpStatus).body("{\"mensaje\":\"" + responseMessage + "\"}");
//    }
//
//    public static ResponseEntity<String> personalizaResponseEntityList(String responseMessage, HttpStatus httpStatus, List<String> details) {
//        String responseBody = "{\"mensaje\":\"" + responseMessage + "\", \"detalles\":" + details.toString() + "}";
//        return ResponseEntity.status(httpStatus).body(responseBody);
//    }


}
