package com.jm.tfg.restImpl;

import com.jm.tfg.Entidades.Product;
import com.jm.tfg.Repo.ProductRepository;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.service.ProductService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "/product")
public class ProductRestImpl {

    @Autowired
    ProductService productService;

    @PostMapping("/add")
    public  ResponseEntity<String> addProduct(@RequestBody Map<String, String> requestMap){
        try {
            return productService.addProduct(requestMap);
        }catch (Exception ex){
            log.error("Error al agregar producto controller", ex);
        }
        return  TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path = "/getall")
    public ResponseEntity<List<Product>> getAllProducts(){
        try {
             return productService.getAllProducts();
        }catch (Exception ex){
            log.error("Error al obtener todos los prodcutos", ex);

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }

    @PostMapping(path = "/update")
    public ResponseEntity<String> updateProduct(@RequestBody Map<String,String> requestMap){
        try {
            return productService.updateProduct(requestMap);
        }catch (Exception ex){
            log.error("Error al update producto en el controlador", ex);
        }
            return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL+"en el controlador de update", HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @DeleteMapping(path = "/delete{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        try{
            return  productService.deleteProduct(id);
        }catch (Exception ex){
            log.error("Error al borrar el producto.",ex);
        }
        return  TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.BAD_REQUEST);
    }




}
