package com.dam.web_cocina.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {

    private Long id;
    private String hashedId;
    private String username;
    private String name;
    private String surname;
    private String genero;
    private String email;
    private String profileImage;
}
