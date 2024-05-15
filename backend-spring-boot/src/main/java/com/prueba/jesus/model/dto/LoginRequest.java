package com.prueba.jesus.model.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank(message = "El usuario es obligatorio")
    String usuario;
    @NotBlank(message = "La contraseña es obligatorio")
    String password;
}
