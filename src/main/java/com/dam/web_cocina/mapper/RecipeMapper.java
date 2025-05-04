package com.dam.web_cocina.mapper;

import com.dam.web_cocina.dto.RecipeDTO;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;

import java.util.List;

public class RecipeMapper {

    private RecipeMapper() {
        // Private constructor
    }

    public static RecipeResponseDTO toDTO(Recipe recipe) {
        RecipeResponseDTO dto = new RecipeResponseDTO();
        dto.setId(recipe.getId());
        dto.setTitle(recipe.getTitle());
        dto.setDescription(recipe.getDescription());
        dto.setCategory(recipe.getCategory());
        dto.setIngredients(recipe.getIngredients());
        dto.setSteps(recipe.getSteps());
        dto.setImageUrl(recipe.getImageUrl());
        dto.setAuthorName(recipe.getAuthor().getName());
        dto.setLikes(recipe.getLikes() != null ? recipe.getLikes().size() : 0);
        return dto;
    }

    public static Recipe toEntity(RecipeDTO dto, User user) {
        Recipe recipe = new Recipe();
        recipe.setTitle(dto.getTitle());
        recipe.setDescription(dto.getDescription());
        recipe.setCategory(dto.getCategory());
        recipe.setIngredients(dto.getIngredients());
        recipe.setSteps(dto.getSteps());
        recipe.setImageUrl(dto.getImageUrl());
        recipe.setAuthor(user);
        return recipe;
    }

    public static List<RecipeResponseDTO> toDTOList(List<Recipe> recipes) {
        return recipes.stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }
}
