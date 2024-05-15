package com.jm.tfg.serviceImpl;

import com.jm.tfg.Entidades.Category;
import com.jm.tfg.Token.JWT.JwtFilter;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.dao.CategoryRepository;
import com.jm.tfg.service.CategoryService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> agregarNuevaCategoria(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
                if(validarCategoria(requestMap, false)) {
                    categoryRepository.save(getCategorydeMap(requestMap, false));
                    return TfgUtils.personalizaResponseEntity("La categoia fue agregada correctamente", HttpStatus.CREATED);
                }
            }else{
                return  TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            log.error("error pocho", ex);

        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private boolean validarCategoria(Map<String, String> requestMap, boolean validarId) {
        /**
         *  El parámetro booleano validarId en el método validarCategoria() indica si se debe validar la existencia de la clave "id" en el mapa de solicitud.
// Si validarId es true, se realiza la validación de la existencia de "id"; si es false, no se realiza dicha validación, esto dependera de si queremos crearlo caso en el que
// sera false ya que no podemos validar un id que aun no existe pero por el contario debemos de hacerlo si queremos hacer un update.*/


        if(requestMap.containsKey("name")){
            if (requestMap.containsKey("id")&& validarId){
                return true;
            }else {
                if (validarId) {
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private Category getCategorydeMap(Map<String, String> requestMap, Boolean esNuevo) {
        Category category = new Category();
        if(esNuevo){
            category.setId(Long.parseLong(requestMap.get("id")));
        }
        category.setName(requestMap.get("name"));
        return category;
    }
    @Override
    // todo rehacer este metodo sin tanto new
    public ResponseEntity<List<Category>> getAllCategoria(String filterValue) {

        try {
            if (filterValue != null && !filterValue.isEmpty()) {
                    if(filterValue.equalsIgnoreCase("true")){
                        log.info("Dentro de get all categoria");
                return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);
                }
            }
            return new ResponseEntity<>(categoryRepository.findAll(), HttpStatus.OK);

        }catch (Exception ex){
            log.error("Error al obtener todas las categorias desde el service", ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }

    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (validarCategoria(requestMap, true)) {
                    Optional<Category> optional = categoryRepository.findById(Long.parseLong(requestMap.get("id")));
                    if (optional.isPresent()) {
                        categoryRepository.save(getCategorydeMap(requestMap, true));
                        return TfgUtils.personalizaResponseEntity("La categoría fue actualizada correctamente", HttpStatus.OK);
                    } else {
                        return TfgUtils.personalizaResponseEntity("La id de la categoría no existe", HttpStatus.NOT_FOUND);
                    }
                }
                }else{
                    return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
                }
        } catch (Exception ex) {
            log.error("Error al actualizar categoría en service", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL + " en el controlador de update", HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
