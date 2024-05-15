package com.prueba.jesus.service;

import java.util.List;
import java.util.stream.Collectors;
import java.sql.Date;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.prueba.jesus.model.dto.UsuarioDto;
import com.prueba.jesus.model.entity.Usuario;
import com.prueba.jesus.repository.RolRepository;
import com.prueba.jesus.repository.UserStatusRepository;
import com.prueba.jesus.repository.UsuarioRespository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

        private final RolRepository rolRepository;
        private final UserStatusRepository statusRepository;
        private final UsuarioRespository userRepository;

        //Get one user
        public UsuarioDto getUserById(Integer id) {
                Usuario usuario = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id " + id));
                return convertirAUsuarioDto(usuario);
        }

        @Transactional
        public UsuarioDto crearUsuario(Integer id, UsuarioDto nuevoUsuarioDto) {
                Usuario usuarioCreador = userRepository.findById(id)
                                .orElseThrow();

                if (userRepository.findByUsername(nuevoUsuarioDto.getUsername()).isPresent()) {
                        throw new RuntimeException(
                                        "El nombre de usuario " + nuevoUsuarioDto.getUsername() + " ya existe.");
                }

                if (usuarioCreador.getRol_rolid().getRolid() != 1 && usuarioCreador.getRol_rolid().getRolid() != 2) {
                        throw new RuntimeException("Solo los usuarios administradores y el usuario gestor pueden crear nuevos usuarios.");
                }
            
                if (usuarioCreador.getRol_rolid().getRolid() == 2 && nuevoUsuarioDto.getRol_rolid() != 3) {
                        throw new RuntimeException("El usuario gestor solo puede crear usuarios cajeros.");
                }
            
                if (nuevoUsuarioDto.getRol_rolid() != 2 && nuevoUsuarioDto.getRol_rolid() != 3) {
                        throw new RuntimeException("El rol del nuevo usuario debe ser gestor o cajero.");
                }
                Usuario nuevoUsuario = Usuario.builder()
                                .username(nuevoUsuarioDto.getUsername())
                                .email(nuevoUsuarioDto.getEmail())
                                .password(nuevoUsuarioDto.getPassword())
                                .usercreate(1)
                                .rol_rolid(rolRepository.findById(nuevoUsuarioDto.getRol_rolid()).orElseThrow(
                                                () -> new RuntimeException("No se encontró el rol con id "
                                                                + nuevoUsuarioDto.getRol_rolid())))
                                .status_statusid(statusRepository.findById(2)
                                                .orElseThrow(
                                                                () -> new RuntimeException(
                                                                                "No se encontró el estado con id "
                                                                                                + nuevoUsuarioDto
                                                                                                                .getStatus_statusid())))
                                .creationdate(Date.valueOf(LocalDate.now()))
                                .build();

                if (usuarioCreador.getRol_rolid().getRolid() == 1) {
                        nuevoUsuario.setUserapproval(1);
                        nuevoUsuario.setDateapproval(Date.valueOf(LocalDate.now()));
                }
                Usuario usuarioGuardado = userRepository.save(nuevoUsuario);
                return convertirAUsuarioDto(usuarioGuardado);
        }

        @Transactional
        public UsuarioDto aprobarUsuario(Integer id) {
                Usuario usuarioExistente = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id " + id));

                usuarioExistente.setUserapproval(1);
                usuarioExistente.setDateapproval(Date.valueOf(LocalDate.now()));

                userRepository.save(usuarioExistente);
                return convertirAUsuarioDto(usuarioExistente);
        }

        public UsuarioDto actualizarUsuario(Integer id, UsuarioDto usuarioDto) {
                Usuario usuario = userRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id " + id));

                usuario.setUsername(usuarioDto.getUsername());
                usuario.setEmail(usuarioDto.getEmail());
                usuario.setPassword(usuarioDto.getPassword());
                usuario.setRol_rolid(rolRepository.findById(usuarioDto.getRol_rolid())
                                .orElseThrow(() -> new RuntimeException(
                                                "No se encontró el rol con id " + usuarioDto.getRol_rolid())));

                Usuario usuarioGuardado = userRepository.save(usuario);
                return convertirAUsuarioDto(usuarioGuardado);
        }

        @Transactional
        public void deleteUser(Integer id) {
                Usuario usuario = userRepository.findById(id).orElse(null);
                if (usuario != null && usuario.getRol_rolid().getRolid() != 1) {
                        userRepository.deleteById(id);
                } else {
                        throw new RuntimeException("No se puede eliminar un usuario con rol id 1");
                }
        }

        public boolean existsById(Integer id) {
                return userRepository.existsById(id);
        }

        public List<UsuarioDto> listUserAll() {
                return userRepository.findAll().stream()
                                .map(usuario -> UsuarioDto.builder()
                                                .userid(usuario.getUserid())
                                                .username(usuario.getUsername())
                                                .email(usuario.getEmail())
                                                .password(usuario.getPassword())
                                                .usercreate(usuario.getUsercreate())
                                                .creationdate(usuario.getCreationdate())
                                                .userapproval(usuario.getUserapproval())
                                                .dateapproval(usuario.getDateapproval())
                                                .rol_rolid(usuario.getRol_rolid().getRolid())
                                                .status_statusid(usuario.getStatus_statusid().getStatusid())
                                                .build())
                                .collect(Collectors.toList());
        }

        private UsuarioDto convertirAUsuarioDto(Usuario usuario) {

                UsuarioDto.UsuarioDtoBuilder builder = UsuarioDto.builder()
                                .userid(usuario.getUserid())
                                .username(usuario.getUsername())
                                .email(usuario.getEmail())
                                .password(usuario.getPassword())
                                .usercreate(usuario.getUsercreate())
                                .creationdate(usuario.getCreationdate())
                                .rol_rolid(usuario.getRol_rolid().getRolid())
                                .status_statusid(usuario.getStatus_statusid().getStatusid());
                if (usuario.getUserapproval() != null && usuario.getDateapproval() != null) {
                        builder.userapproval(usuario.getUserapproval());
               
                        builder.dateapproval(usuario.getDateapproval());
                }

                return builder.build();
        }
        public List<UsuarioDto> listCashiers() {
                return userRepository.findAll().stream()
                                .filter(usuario -> usuario.getRol_rolid().getRolid() == 3)
                                .map(this::convertirAUsuarioDto)
                                .collect(Collectors.toList());
        }



        


 

}
