package com.IOT.IOT_system.repository;

import com.IOT.IOT_system.DTO.UserDTO;
import com.IOT.IOT_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories
@Repository
public interface UserDTO_Repository extends JpaRepository<UserDTO,Long> {
    Optional<UserDTO> findById(Long user_id);
    void deleteById(Long id);
}
