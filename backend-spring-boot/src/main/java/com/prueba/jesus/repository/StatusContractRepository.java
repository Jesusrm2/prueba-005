package com.prueba.jesus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.StatusContract;

public interface StatusContractRepository extends JpaRepository<StatusContract, Integer>{
    boolean existsByDescription(String description);
    StatusContract findByDescription(String description);

}
