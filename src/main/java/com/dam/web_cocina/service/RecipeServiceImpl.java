package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.RecipeNotFoundException;
import com.dam.web_cocina.common.utils.AuthUtil;
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
    private final AuthUtil authUtil;

    public RecipeServiceImpl(
            RecipeRepository recipeRepository, UserRepository userRepository, AuthUtil authUtil
    ) {
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.authUtil = authUtil;
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
        User currentUser = authUtil.getCurrentUser();
        return RecipeMapper.toDTOList(recipeRepository.findAll(), currentUser);
    }

    @Override
    public RecipeResponseDTO findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        User currentUser = authUtil.getCurrentUser();
        return RecipeMapper.toDTO(recipe, currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findByAuthorId(Long authorId) {
        User currentUser = authUtil.getCurrentUser();
        return RecipeMapper.toDTOList(recipeRepository.findByAuthorId(authorId), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findByTitle(String title) {
        User currentUser = authUtil.getCurrentUser();
        return RecipeMapper.toDTOList(recipeRepository.findByTitleContainingIgnoreCase(title), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findByIngredient(String ingredient) {
        User currentUser = authUtil.getCurrentUser();
        return RecipeMapper.toDTOList(recipeRepository.findByIngredientsContainingIgnoreCase(ingredient), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findLastRecipes() {
        User currentUser = authUtil.getCurrentUser();
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        return RecipeMapper.toDTOList(recipeRepository.findAll(pageable).getContent(), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findTopLikedRecipes() {
        User currentUser = authUtil.getCurrentUser();
        Pageable pageable = PageRequest.of(0, 5);
        return RecipeMapper.toDTOList(recipeRepository.findTopLiked(pageable), currentUser);
    }
}
