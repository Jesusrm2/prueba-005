package com.prueba.jesus.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.jesus.model.dto.UsuarioDto;
import com.prueba.jesus.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("user/{id}")
    public ResponseEntity<?> getUser(@PathVariable Integer id) {
        try {
            if (!userService.existsById(id)) {
                return ResponseEntity.badRequest().body("El usuario con id " + id + " no existe.");
            }
            return ResponseEntity.ok().body(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("users")
    public ResponseEntity<?> getUsers() {
        try {
            return ResponseEntity.ok().body(userService.listUserAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("user/{id}")
    public ResponseEntity<?> createUser(@Validated @RequestBody UsuarioDto userDto, @PathVariable Integer id) {
        try {
            if (!userService.existsById(id)) {
               return ResponseEntity.badRequest().body("El usuario con id " + id + " no existe.");
            }

            UsuarioDto userSave = userService.crearUsuario(id, userDto);  
            return ResponseEntity.ok().body(userSave);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("user/{id}")
    public ResponseEntity<?> updateUser(@Validated @RequestBody UsuarioDto userDto, @PathVariable Integer id) {
        try {
            if (!userService.existsById(id)) {
                return ResponseEntity.badRequest().body("El usuario con id " + id + " no existe.");
            }
            UsuarioDto userUpdated = userService.actualizarUsuario(id, userDto);  
            return ResponseEntity.ok().body(userUpdated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("user/aprobar/{id}")
    public ResponseEntity<?> aprobarUser( @PathVariable Integer id) {
        try {
            if (!userService.existsById(id)) {
                return ResponseEntity.badRequest().body("El usuario con id " + id + " no existe.");
            }
            UsuarioDto userUpdated = userService.aprobarUsuario(id);
            
            return ResponseEntity.ok().body(userUpdated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //Delete
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        try {
            if (!userService.existsById(id)) {
                return ResponseEntity.badRequest().body("El usuario con id " + id + " no existe.");
            }
            userService.deleteUser(id);  
            return ResponseEntity.ok().body("Usuario eliminado");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //Listar los usuarios cajeros
    @GetMapping("users/cashier")
    public ResponseEntity<?> getUsersCashier() {
        try {
            List<UsuarioDto> usersCashier = userService.listCashiers();
            if (usersCashier.isEmpty() || usersCashier == null) {
                return ResponseEntity.ok("No hay cajeros registrados");
            }
            return ResponseEntity.ok(usersCashier);
           
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
