package com.dam.web_cocina.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "El nombre de usuario es obligatorio")
    @Size(max = 30, message = "El nombre de usuario no puede tener más de 30 caracteres")
    private String username;

    private String nombre;

    private String apellidos;

    private String genero;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Email no válido")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;
}
