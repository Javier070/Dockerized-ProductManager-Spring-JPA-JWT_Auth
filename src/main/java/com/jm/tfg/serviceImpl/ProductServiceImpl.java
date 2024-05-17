package com.jm.tfg.serviceImpl;

import com.jm.tfg.Entidades.Product;
import com.jm.tfg.Token.JWT.JwtFilter;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.service.ProductService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ProductServiceImpl   implements ProductService {

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<List<Product>> getAllProducts() { //mondongo aqui he metido admin por probar
        try {
            if (jwtFilter.isAdmin()) {


            }else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ArrayList<>());
            }
        }catch (Exception ex){
            log.error("Error al obtener los productos", ex);

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }

    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try {
          if  (jwtFilter.isAdmin()){
              if (validarProductMap(requestMap,false)){

              }

          }
          else {
              return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(TfgConstants.ACCESO_NO_AUTORIZADO);
          }
        }catch (Exception ex){
            log.error("Error al agregar producto servicio", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validarProductMap(Map<String, String> requestMap, boolean b) {
        return false;
    }


}
