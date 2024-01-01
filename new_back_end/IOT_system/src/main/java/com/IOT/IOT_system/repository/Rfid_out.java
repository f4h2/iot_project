package com.IOT.IOT_system.repository;

import com.IOT.IOT_system.model.RFID;
import com.IOT.IOT_system.model.RFID_out;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
@Transactional
public interface Rfid_out extends JpaRepository<RFID_out,Long> {

}
