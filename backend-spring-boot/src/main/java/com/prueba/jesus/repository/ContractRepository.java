package com.prueba.jesus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Client;
import com.prueba.jesus.model.entity.Contract;

public interface ContractRepository extends JpaRepository<Contract, Integer>{


}
