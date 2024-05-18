package com.jm.tfg.serviceImpl;
import com.jm.tfg.Entidades.Category;
import com.jm.tfg.Token.JWT.JwtFilter;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.Repo.CategoryRepository;
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
    public ResponseEntity<List<Category>> getAllCategoria() { // si filterValue es true, entonces devuelve todas las categorias, si es flase solo las que contienen al menos un producto
        List<Category> arrayError = new ArrayList<>();// Cuando la respuesta des de error, se devuelve un array vacio
        List<Category> categories = categoryRepository.findAll();
        try {
            return ResponseEntity.status(HttpStatus.OK).body(categories);
        } catch (Exception ex) {
            log.error("Error al obtener todas las categorias desde el service", ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(arrayError);
    }





    @Override
    public ResponseEntity<String> agregarNuevaCategoria(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (TfgUtils.validarCategoriaProducto(requestMap, false)) {
                    categoryRepository.save(getCategorydeMap(requestMap, false));
                    return TfgUtils.personalizaResponseEntity("La categoría fue agregada correctamente", HttpStatus.CREATED);
                }
                return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS, HttpStatus.BAD_REQUEST);
            }
                return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
        } catch (Exception ex) {
            log.error("Error al agregar categoría", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Override
    public ResponseEntity<String> updateCategory(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()) {
                if (TfgUtils.validarCategoriaProducto(requestMap, true)) {
                    Optional<Category> optional = categoryRepository.findById(Long.parseLong(requestMap.get("id")));
                    if (optional.isPresent()) {
                        categoryRepository.save(getCategorydeMap(requestMap, true));
                        return TfgUtils.personalizaResponseEntity("La categoría fue actualizada correctamente", HttpStatus.OK);
                    } else {
                        return TfgUtils.personalizaResponseEntity("El id: "+requestMap.get("id")+" asociado a la categoría: " + requestMap.get("name") + " no existe", HttpStatus.NOT_FOUND);
                    }
                }else{
                    return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS, HttpStatus.BAD_REQUEST);
                }
            } else {
                return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            log.error("Error al actualizar categoría en service", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL + " en el serivce de update", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteCategory(Long id) {
        try {
            if (jwtFilter.isAdmin()) {
                Optional <Category>  optional = categoryRepository.findById(id);
                if (optional.isPresent()) {
                    categoryRepository.deleteById(id);
                    return TfgUtils.personalizaResponseEntity("La categoría fue eliminada correctamente", HttpStatus.OK);
                }
                return TfgUtils.personalizaResponseEntity("El id no existe", HttpStatus.NOT_FOUND);
            }else {
                return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception ex) {
            log.error("Error al actualizar categoría en service", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL + " en el serivce de update", HttpStatus.INTERNAL_SERVER_ERROR);
    }





    /**
     * Crea un objeto Category a partir de un mapa de datos.
     *
     * <p>Este método construye y retorna un objeto {@code Category} utilizando la información proporcionada
     * en el mapa {@code requestMap}. Si el parámetro {@code isAdd} es {@code true}, también establece el ID de la categoría.</p>
     *
     * @param requestMap un mapa que contiene la información de la categoría, con claves como "id" y "name"
     * @param isAdd un booleano que indica si se debe establecer el ID de la categoría; {@code true} para establecer el ID, {@code false} para no hacerlo
     * @return un objeto {@code Category} construido a partir de los datos proporcionados en {@code requestMap}
     */
    private Category getCategorydeMap(Map<String, String> requestMap, Boolean isAdd) {
        Category category = new Category();
        category.setName(requestMap.get("name"));
        if (isAdd) {
            category.setId(Long.parseLong(requestMap.get("id")));
        }
        return category;
    }





}
