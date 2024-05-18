package com.jm.tfg.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TfgUtils {
    /**
     * Crea un objeto ResponseEntity personalizado .
     *
     * <p>Esta funcion crea un objeto ResponseEntity adaptado a las necesidades de la respuesta.</p>
     * <p>Toma un mensaje de respuesta y un código de estado HTTP, y devuelve un ResponseEntity con el mensaje en el cuerpo
     * y el código de estado especificado.</p>
     *
     * @param responseMessage el mensaje de respuesta a incluir en el cuerpo del ResponseEntity
     * @param httpStatus el código de estado HTTP que se utilizará en el ResponseEntity
     * @return un objeto ResponseEntity con el mensaje de respuesta y el código de estado especificados
     */
    public static ResponseEntity<String> personalizaResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body("{\"mensaje\":\"" + responseMessage + "\"}");
    }






     /**
         * **Valida la existencia de las claves "name" y opcionalmente "id" en el mapa de solicitud**.
         *
         * <p>El parámetro booleano {@code validarId}
         * indica si se debe validar la existencia de la clave "id" en el mapa de solicitud.</p>
         *
         * <p>Si {@code validarId} = {@code true}, se realiza la validación de la existencia de "id";
         * si es {@code validarId} ={@code false}, no se realiza dicha validación.
         *
         * Esto depende de si queremos crear un nuevo objeto (caso en el que {@code validarId} será {@code false}, ya que no queremos validar
         * un ID que aún no existe)
         *
         * Si queremos hacer una actualización (caso en el que
         * {@code validarId} será {@code true} ya que el ID debe existir).</p>
         *
         * @param requestMap el mapa de solicitud que contiene las claves y valores a validar
         * @param validarId un booleano que indica si se debe validar la existencia de la clave "id"
         * @return {@code true} si la validación es exitosa, {@code false} en caso contrario
         */
        public static boolean validarCategoriaProducto(Map<String, String> requestMap, boolean validarId) {
            if (requestMap.containsKey("name")) {
                if (validarId) {
                    return requestMap.containsKey("id");
                }
                return true;
            }
            return false;
        }
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



