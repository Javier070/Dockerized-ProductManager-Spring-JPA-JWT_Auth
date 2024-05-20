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
     * @param httpStatus      el código de estado HTTP que se utilizará en el ResponseEntity
     * @return un objeto ResponseEntity con el mensaje de respuesta y el código de estado especificados
     */
    public static ResponseEntity<String> personalizaResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body("{\"mensaje\":\"" + responseMessage + "\"}");
    }

    /**
     * Valida la existencia de las claves necesarias en el mapa de solicitud.
     *
     * <p>El parámetro booleano {@code validarId} indica si se debe validar la existencia de la clave "id" en el mapa de solicitud.</p>
     * <p>El parámetro booleano {@code esCategory} indica si se está validando una categoría o un producto.</p>
     * <p>Para categorías, se valida la existencia de la clave "name". Si {@code validarId} es {@code true}, también se valida la clave "id".</p>
     * <p>Para productos, se valida la existencia de las claves "name", "category_fk", "description", "price" y "status".
     * Si {@code validarId} es {@code true}, también se valida la clave "id".</p>
     *
     * @param requestMap el mapa de solicitud que contiene las claves y valores a validar
     * @param validarId  un booleano que indica si se debe validar la existencia de la clave "id"
     * @param esCategory un booleano que indica si se está validando una categoría (true) o un producto (false)
     * @return {@code true} si la validación es exitosa, {@code false} en caso contrario
     */
    public static boolean validarCategoriaProducto(Map<String, String> requestMap, boolean validarId, boolean esCategory) {
        if (esCategory) {
            if (requestMap.containsKey("name")) {
                if (validarId) {
                    return requestMap.containsKey("id");
                }
                return true;
            }
            return false;
        }
        if (    requestMap.containsKey("category_fk")  &&
                requestMap.containsKey("name") &&
                requestMap.containsKey("price") &&
                requestMap.containsKey("description")




        ) {

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



