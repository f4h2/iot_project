package com.IOT.IOT_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface Stream_bit_Repo extends JpaRepository<com.IOT.IOT_system.model.Stream_bit, Integer> {

    // Tìm model có id cao nhất
    @Query("SELECT s FROM Stream_bit s WHERE s.id = (SELECT MAX(s2.id) FROM Stream_bit s2)")
    com.IOT.IOT_system.model.Stream_bit findTopById();
}