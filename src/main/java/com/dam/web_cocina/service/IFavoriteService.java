package com.dam.web_cocina.service;

import com.dam.web_cocina.dto.RecipeResponseDTO;

import java.util.List;

public interface IFavoriteService {

    void favorite(Long recipeId);

    void unfavorite(Long recipeId);

    boolean isFavorite(Long recipeId);

    List<RecipeResponseDTO> getFavoritesByCurrentUser();
}
