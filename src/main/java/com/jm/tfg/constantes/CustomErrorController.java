package com.jm.tfg.constantes;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Lógica para manejar el error y mostrar una página personalizada
        return "error"; // Devuelve el nombre de la plantilla o vista que quieres mostrar
    }


    public String getErrorPath() {
        return "/error";
    }
}
