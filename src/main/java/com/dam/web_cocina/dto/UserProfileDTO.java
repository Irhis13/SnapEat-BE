package com.dam.web_cocina.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
@Data
public class UserProfileDTO {

     private String username;

    private String nombre;

    private String apellidos;

    private String genero;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    private String imageUrl;
}
