package com.jm.tfg.Repo;

import com.jm.tfg.Entidades.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
// todo documentar esto
        @Modifying
        @Transactional
        @Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
        void updateStatus(String status, long id);

        List<Product> findByCategory_Id(Long id);

//        @Query("")
//    List<Product> getProductsByCategory( Long id);
//

    //Spring Data JPA no deriva consultas UPDATE de manera automática como lo hace con SELECT o DELETE...
//La anotación @Modifying se utiliza para mejorar la anotación @Query para que podamos ejecutar no solo consultas SELECT ,
// sino también consultas INSERT , UPDATE , DELETE e incluso DDL .


    //DELETE: Spring Data JPA puede derivar automáticamente consultas DELETE a partir del nombre del método.

    /***
     * aunque puedes encontrar recomendaciones de métodos como updateBy o updateByProduct en algunas herramientas de autocompletado,
     * estos no generan automáticamente las consultas UPDATE en la base de datos.
     * Las operaciones de actualización en Spring Data JPA requieren una consulta explícita.
     */

    /**
     *
     * @param status
     * @param id
     */



//No se si a esta funcion hay q ponerle:
    // @Modifying
//    @Transactional
//    void updateStatus(String status, long id);
//    void updateByProduct(Product product);
//
//    void updateByStatusAndId(String status, long id);
//    void save

}