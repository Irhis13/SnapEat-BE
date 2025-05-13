package com.dam.web_cocina.dto;

import com.dam.web_cocina.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {

    private Long id;

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no puede superar los 100 caracteres")
    private String title;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 1000, message = "La descripción no puede superar los 1000 caracteres")
    private String description;

    @NotNull(message = "La categoría es obligatoria")
    private Category category;

    @NotBlank(message = "Los ingredientes son obligatorios")
    private String ingredients;

    @NotEmpty(message = "Debes añadir al menos un paso")
    private List<@NotBlank(message = "Ningún paso puede estar vacío") String> steps;

    private String imageUrl;
}
