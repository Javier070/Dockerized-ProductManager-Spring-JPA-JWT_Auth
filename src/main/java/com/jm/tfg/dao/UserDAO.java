package com.jm.tfg.dao;

import com.jm.tfg.Entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDAO extends JpaRepository<User, Long> {
//    long deleteByName1(String name);
//
//    void removeByName(String name);
//
//    GeoResult<User> findByNam1e(String name);
//
//    Optional<User> findByName(String name);
//
//    @Transactional
//    @Modifying
////    @Query("delete from User u where u.contactNumber = ?1")
//    void deleteByContactNumber(String contactNumber);


//    User findByEmailId(("email")String email); //(@Param("email")  indica cómo debe nombrarse el parámetro en la consulta SQL
    User findByEmail(@Param("email") String email);

    /**
     *Cuando usar @Param  User findByEmailId(@Param("email") String email)
     *  User findByEmailId(@Param("email") String email);
     * @Param se pone cuando el valor en base de datos y en la clase java no son iguales y hace falta especificarlo
     */
}