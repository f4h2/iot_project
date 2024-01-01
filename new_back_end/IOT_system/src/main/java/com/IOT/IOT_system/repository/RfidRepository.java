package com.IOT.IOT_system.repository;

import com.IOT.IOT_system.model.RFID;
import feign.Param;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@EnableJpaRepositories
@Repository
@Transactional
public interface RfidRepository extends JpaRepository<RFID,Long> {
//    @Query(value = "SELECT * FROM RFID WHERE IDx=?",nativeQuery = true)
    Optional<RFID> findByIDx(String IDx);

    @Modifying
    @Query(value ="delete from RFID r where r.IDx = :IDx")
    void deleteRFID(@Param("IDx") String IDx);
//       Long deleteByIDx( String IDx);

}