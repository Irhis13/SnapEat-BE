package com.dam.web_cocina.dto;

import lombok.Data;

import java.util.List;

@Data
public class RecipeDTO {
    private Long id;
    private String title;
    private String description;
    private String ingredients;
    private List<String> steps;
    private String imageUrl;
    private Long authorId;
}

