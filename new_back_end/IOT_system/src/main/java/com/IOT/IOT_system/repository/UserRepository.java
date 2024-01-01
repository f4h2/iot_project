package com.IOT.IOT_system.repository;

import com.IOT.IOT_system.model.User;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@EnableJpaRepositories
@Repository
    public interface UserRepository extends JpaRepository<User,Long> {

//    @Query(value = "SELECT * FROM users WHERE ten_tk=?",nativeQuery = true)
//    Optional<User> findByUserName(String ten_tk);
    @Query(value = "SELECT * FROM users WHERE tk_user=? AND mk_user=?",nativeQuery = true)
    Optional<User> findOneByTkAndMk(String tk_user, String mk_user);
    Optional<User> findById(Long id);
    @Query(value = "SELECT * FROM users WHERE ten_tk = :tenTk", nativeQuery = true)
    Optional<User> findByTen_tk(@Param("tenTk") String tenTk);

}
