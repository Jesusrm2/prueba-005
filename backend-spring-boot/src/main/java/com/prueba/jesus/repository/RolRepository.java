package com.prueba.jesus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Rol;


public interface RolRepository extends JpaRepository<Rol, Integer>{
    boolean existsByRolname(String rolname);
    Optional<Rol> findByRolname(String rolname);
}
