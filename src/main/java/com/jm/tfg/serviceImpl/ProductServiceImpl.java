package com.jm.tfg.serviceImpl;

import com.jm.tfg.Entidades.Category;
import com.jm.tfg.Entidades.Product;
import com.jm.tfg.Repo.ProductRepository;
import com.jm.tfg.Token.JWT.JwtFilter;
import com.jm.tfg.constantes.TfgConstants;
import com.jm.tfg.service.ProductService;
import com.jm.tfg.utils.TfgUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    JwtFilter jwtFilter;


    @Override
    public ResponseEntity<List<Product>> getAllProducts() { //mondongo aqui he metido admin por probar
        try {
            List<Product> products = productRepository.findAll(); // Call findAll once and store the result
            return ResponseEntity.status(HttpStatus.OK).body(products);// esta mal
        }catch (Exception ex){
            log.error("Error al obtener los productos", ex);


        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>()); //todo camboa esto, pongo bad request para diferenciar
    }




    @Override
    public ResponseEntity<String> addProduct(Map<String, String> requestMap) {
        try {
          if  (jwtFilter.isAdmin()){
              if (TfgUtils.validarCategoriaProducto(requestMap,false,false)) {
                  productRepository.save( getProductMap(requestMap,false));
                return TfgUtils.personalizaResponseEntity("EL producto fue agregado correctamente.", HttpStatus.CREATED);
              }else {
                  return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS, HttpStatus.BAD_REQUEST);
              }
          }
          else {
              return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
          }
        } catch (DataIntegrityViolationException ex) {
            // Manejar el error específico de insertar un categoria que no existe en un producto
            return TfgUtils.personalizaResponseEntity("Error al agregar el producto: la categoría especificada no existe.", HttpStatus.BAD_REQUEST);
        }catch (Exception ex){
            log.error("Error al agregar producto servicio", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL+"servicio", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if (jwtFilter.isAdmin()){
                if(TfgUtils.validarCategoriaProducto(requestMap, true, false)){
                    Optional<Product> optional = productRepository.findById(Long.parseLong(requestMap.get("id")));
                    if (optional.isPresent()) {
                        Product productFromMap = getProductMap(requestMap,true);
                        productRepository.save(productFromMap);
                        return TfgUtils.personalizaResponseEntity("El producto fue actualizado correctamente", HttpStatus.OK);
                    }else{
                        return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS+"para el id",HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return TfgUtils.personalizaResponseEntity(TfgConstants.DATOS_NO_VALIDOS,HttpStatus.BAD_REQUEST);
                }
            }else {
                return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO,HttpStatus.UNAUTHORIZED);
            }

        }catch (JpaObjectRetrievalFailureException  ex) {
            // Manejar el error específico de insertar un categoria que no existe en un producto
            return TfgUtils.personalizaResponseEntity("Error al agregar el producto: la category_fk no existe.", HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            log.error("Error al update producto servicio", ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL+"servicio", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            if (jwtFilter.isAdmin()){
                Optional <Product> optional = productRepository.findById(id);
                if (optional.isPresent()){
                    productRepository.deleteById(id);
                    return  TfgUtils.personalizaResponseEntity(TfgConstants.OPERACION_EXITOSA, HttpStatus.OK);
                }
                return TfgUtils.personalizaResponseEntity(TfgConstants.INCORRECT_ID, HttpStatus.NOT_FOUND);
           }
          return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
        }catch (Exception ex){
            log.error("Error al borrar el producto servicio.",ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<String> updateStatusProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()){
               Optional optional= productRepository.findById(Long.parseLong(requestMap.get("id")));
               if (optional.isPresent()){
                   productRepository.updateStatus(requestMap.get("status"),Long.parseLong(requestMap.get("id")));
                   return TfgUtils.personalizaResponseEntity(TfgConstants.OPERACION_EXITOSA, HttpStatus.OK);
               }
                return TfgUtils.personalizaResponseEntity(TfgConstants.INCORRECT_ID, HttpStatus.NOT_FOUND);
            } else{
                return TfgUtils.personalizaResponseEntity(TfgConstants.ACCESO_NO_AUTORIZADO, HttpStatus.UNAUTHORIZED);
            }

        }catch (Exception ex){
            log.error("Error al update el status product servicio.",ex);

        }
             return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> getProductsByCategory(Long id) {
        try{
            List<Product> products = productRepository.findByCategory_Id(id);
            if (products.isEmpty()) {
                log.error("Error al obtener los productos");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ArrayList<>());

            }
            return ResponseEntity.status(HttpStatus.OK).body(products);
        }catch (Exception ex){
            log.error("Error al obtener productos por categoria servicio.",ex);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
    }

    @Override
    public ResponseEntity<String> getProductById(Long id) {
        try {
            Optional<Product> optional = productRepository.findById(id);
            if (optional.isPresent()) {
                return TfgUtils.personalizaResponseEntity(String.valueOf(optional), HttpStatus.OK);
            } else {

                return TfgUtils.personalizaResponseEntity("Producto no encontrado", HttpStatus.NOT_FOUND);
            }
        }catch (Exception ex){
            log.error("Error al obtener producto por id servicio.",ex);
        }
        return TfgUtils.personalizaResponseEntity(TfgConstants.ALGO_SALE_MAL,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Este método crea y configura un objeto {@code Product} a partir de un {@code requestMap}
     *
     * Si la operación es de update, se establece el ID del producto con {@code isAdd}
     *
     * @param requestMap Un mapa que contiene los datos de la solicitud, donde las claves son nombres de atributos y los valores son los valores correspondientes.
     * @param isAdd      Un booleano que indica si la operación es una adición de producto.
     * @return           Retorna un objeto de tipo Product creado a partir de los datos de la solicitud.
     */
    private Product getProductMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Long.parseLong(requestMap.get("category_fk"))); // em esta linea le decimos a que categoria va a perteneceer

        Product product = new Product();
        product.setCategory(category);


        // Establecer el ID del producto si es una operación update
        if (isAdd) {
            product.setId(Long.parseLong(requestMap.get("id")));
        }

        else {
            product.setStatus("true");
        }
        // Establecer otros atributos del producto
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Double.parseDouble(requestMap.get("price")));
        return product;
    }



}
