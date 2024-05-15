package com.prueba.jesus.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prueba.jesus.model.entity.Usuario;

public interface UsuarioRespository extends JpaRepository<Usuario, Integer>{
    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByUsername(String username);
}
