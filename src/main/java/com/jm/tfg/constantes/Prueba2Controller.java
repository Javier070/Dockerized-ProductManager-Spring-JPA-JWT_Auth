package com.jm.tfg.constantes;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
public class Prueba2Controller {

    @PostMapping("/agregar")
    public String agregarNuevaCategoria(@RequestBody Map<String, String> requestMap) {
        boolean isAdmin = false; // Supongamos que el usuario es un administrador  llllllTODO CAMBIAR DE TRUE A FALSE Y VICEVERSA
        boolean isValidCategory = true; // Supongamos que la categoría es válida
        int resultado = 10/0;
        try {
            if (isAdmin) {

                if (isValidCategory) {
                    // Aquí debes agregar la lógica para agregar la nueva categoría, por ejemplo:
                    // Category category = new Category();
                    // category.setName(requestMap.get("name"));
                    // categoryRepository.save(category);

                    return "La categoría fue agregada correctamente";
                } else {
                    return "Categoría no válida";
                }
            } else {
                return "No eres administrador";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Algo salió mal";
        }
    }
}
