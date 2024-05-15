package com.prueba.jesus.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;


import com.prueba.jesus.model.entity.UserStatus;

public interface UserStatusRepository extends JpaRepository<UserStatus, Integer>{
    boolean existsByDescription(String description);
    Optional<UserStatus> findByDescription(String description);

}
