package com.dam.web_cocina.service;

import com.dam.web_cocina.dto.RecipeDTO;
import com.dam.web_cocina.dto.RecipeResponseDTO;

import java.util.List;

public interface IRecipeService {

    RecipeResponseDTO save(RecipeDTO dto);

    void delete(Long id);

    List<RecipeResponseDTO> findAll();

    RecipeResponseDTO findById(Long id);

    List<RecipeResponseDTO> findByAuthorId(Long authorId);

    List<RecipeResponseDTO> findByTitle(String title);

    List<RecipeResponseDTO> findByIngredient(String ingredient);
}
