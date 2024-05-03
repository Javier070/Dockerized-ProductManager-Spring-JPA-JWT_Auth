package com.jm.tfg.dao;

import com.jm.tfg.Entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
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
}