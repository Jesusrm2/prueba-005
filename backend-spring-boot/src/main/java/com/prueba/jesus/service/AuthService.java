package com.prueba.jesus.service;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.prueba.jesus.model.dto.LoginRequest;
import com.prueba.jesus.model.dto.UsuarioDto;
import com.prueba.jesus.model.entity.UserStatus;
import com.prueba.jesus.model.entity.Usuario;
import com.prueba.jesus.repository.UserStatusRepository;
import com.prueba.jesus.repository.UsuarioRespository;

import jakarta.transaction.Transactional;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRespository usuarioRespository;

        private final UserStatusRepository statusRepository;


    @Transactional
    public UsuarioDto login(LoginRequest loginRequest) {
        Usuario user = usuarioRespository.findByEmail(loginRequest.getUsuario())
            .orElseThrow(() -> new IllegalArgumentException("Correo no encontrado"));

        if (user.getUserapproval() == null || user.getUserapproval() == 0) {
            throw new IllegalArgumentException("Usuario no aprobado");
        }
        if (!user.getPassword().equals(loginRequest.getPassword())) {
            throw new IllegalArgumentException("Contraseña incorrecta");
        }

    UserStatus status = statusRepository.findById(1)
    .orElseThrow(() -> new RuntimeException("No se encontró el estado con id 2"));

    user.setStatus_statusid(status);
        return UsuarioDto.builder()
            .userid(user.getUserid())
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .creationdate(user.getCreationdate())
            .usercreate(user.getUsercreate())
            .userapproval(user.getUserapproval())
            .dateapproval(user.getDateapproval())
            .rol_rolid(user.getRol_rolid().getRolid())
            .status_statusid(user.getStatus_statusid().getStatusid())
            .build();
    }
    //logout 
    @Transactional
    public Usuario logout(Integer id) {
        Usuario user = usuarioRespository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        UserStatus status = statusRepository.findById(2)
            .orElseThrow(() -> new RuntimeException("No se encontró el estado con id 2"));
        user.setStatus_statusid(status);
        usuarioRespository.save(user);
        return user;


    }

}
