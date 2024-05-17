package com.jm.tfg.restImpl;

import com.jm.tfg.Entidades.Product;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.service.ProductService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/products")
public class ProductRestImpl {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public  ResponseEntity<String> addProduct(@RequestBody Map<String, String> requestMap){
        try {
                productService.addProduct(requestMap);
        }catch (Exception ex){
            log.error("Error al agregar producto controller", ex);
        }
        return  TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }









    @PostMapping(path = "/getall")
    public ResponseEntity<List<Product>> getAllProducts(){
        try {
            productService.getAllProducts();
        }catch (Exception ex){
            log.error("Error al obtener todos los prodcutos", ex);

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }

}
