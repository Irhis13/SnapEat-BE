package com.dam.web_cocina.service;

import com.dam.web_cocina.dto.RecipeResponseDTO;

public interface ILikeService {

    RecipeResponseDTO like(Long recipeId);

    void unlike(Long recipeId);

    RecipeResponseDTO findById(Long id);

    boolean isLiked(Long recipeId);

    long countLikes(Long recipeId);
}
