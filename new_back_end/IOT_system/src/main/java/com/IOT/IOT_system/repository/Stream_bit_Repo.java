package com.IOT.IOT_system.repository;

import com.IOT.IOT_system.model.Stream_bit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@EnableJpaRepositories
@Repository
public interface Stream_bit_Repo extends JpaRepository<Stream_bit, Long> {

    // Tìm model có id cao nhất
    @Query("SELECT s FROM Stream_bit s ORDER BY s.id DESC")
    List<Stream_bit> findTopById();
    List<Stream_bit> findAll();
}