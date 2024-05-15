package com.prueba.jesus.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Integer>{

    boolean existsByServicename(String servicename);

}
