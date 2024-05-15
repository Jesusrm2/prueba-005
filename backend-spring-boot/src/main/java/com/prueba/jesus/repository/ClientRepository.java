package com.prueba.jesus.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Integer>{

    Optional<Client> findByIdentification(String identification);
}
