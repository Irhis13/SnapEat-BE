package com.dam.web_cocina.service;

import com.dam.web_cocina.dto.RecipeDTO;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.mapper.RecipeMapper;
import com.dam.web_cocina.repository.RecipeRepository;
import com.dam.web_cocina.repository.UserRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
public class RecipeServiceImpl implements IRecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository, UserRepository userRepository) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RecipeResponseDTO save(RecipeDTO dto) {
        User user = userRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getAuthorId()));

        Recipe recipe;
        if (dto.getId() != null) {
            recipe = recipeRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + dto.getId()));
            recipe.setTitle(dto.getTitle());
            recipe.setDescription(dto.getDescription());
            recipe.setIngredients(dto.getIngredients());
            recipe.setSteps(dto.getSteps());
            recipe.setImageUrl(dto.getImageUrl());
            recipe.setAuthor(user);
        } else {
            recipe = RecipeMapper.toEntity(dto, user);
        }

        return RecipeMapper.toDTO(recipeRepository.save(recipe));
    }

    @Override
    public void delete(Long id) {
        recipeRepository.deleteById(id);
    }

    @Override
    public List<RecipeResponseDTO> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }

    @Override
    public RecipeResponseDTO findById(Long id) {
        return recipeRepository.findById(id)
                .map(RecipeMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Recipe not found"));
    }

    @Override
    public List<RecipeResponseDTO> findByAuthorId(Long authorId) {
        return recipeRepository.findByAuthorId(authorId)
                .stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }

    @Override
    public List<RecipeResponseDTO> findByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }

    @Override
    public List<RecipeResponseDTO> findByIngredient(String ingredient) {
        return recipeRepository.findByIngredientsContainingIgnoreCase(ingredient)
                .stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }

    @Override
    public List<RecipeResponseDTO> findLastRecipes() {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Recipe> recetas = recipeRepository.findAll(pageable).getContent();

        return recetas.stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }

    @Override
    public List<RecipeResponseDTO> findTopLikedRecipes() {
        Pageable pageable = PageRequest.of(0, 5);
        List<Recipe> topLiked = recipeRepository.findTopLiked(pageable);

        return topLiked.stream()
                .map(RecipeMapper::toDTO)
                .toList();
    }
}
