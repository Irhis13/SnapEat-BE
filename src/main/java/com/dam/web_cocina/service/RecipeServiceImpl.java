package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.RecipeNotFoundException;
import com.dam.web_cocina.common.exceptions.UnauthorizedAccessException;
import com.dam.web_cocina.common.utils.AuthUtil;
import com.dam.web_cocina.dto.RecipeDTO;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.mapper.RecipeMapper;
import com.dam.web_cocina.repository.RecipeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class RecipeServiceImpl implements IRecipeService {

    private final RecipeRepository recipeRepository;
    private final IImageService imageService;

    public RecipeServiceImpl(
            RecipeRepository recipeRepository,
            IImageService imageService
    ) {
        this.recipeRepository = recipeRepository;
        this.imageService = imageService;
    }

    @Override
    public RecipeResponseDTO save(RecipeDTO dto) {
        User currentUser = AuthUtil.getCurrentUser();

        if (currentUser == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }

        Recipe recipe;

        if (dto.getId() != null) {
            recipe = recipeRepository.findById(dto.getId())
                    .orElseThrow(() -> new RecipeNotFoundException(dto.getId()));

            if (!recipe.getAuthor().getId().equals(currentUser.getId())) {
                throw UnauthorizedAccessException.forEdit();
            }

            recipe.setTitle(dto.getTitle());
            recipe.setDescription(dto.getDescription());
            recipe.setIngredients(dto.getIngredients());
            recipe.setSteps(dto.getSteps());
            recipe.setImageUrl(dto.getImageUrl());
            recipe.setCategory(dto.getCategory());
        } else {
            recipe = RecipeMapper.toEntity(dto, currentUser);
        }

        return RecipeMapper.toDTO(recipeRepository.save(recipe));
    }

    @Override
    public RecipeResponseDTO saveWithImage(RecipeDTO dto, MultipartFile imagen) {
        if (imagen != null && !imagen.isEmpty()) {
            String imageUrl = imageService.saveImage(imagen);
            dto.setImageUrl(imageUrl);
        }
        return save(dto);
    }

    @Override
    public void delete(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        User currentUser = AuthUtil.getCurrentUser();
        if (currentUser == null || !recipe.getAuthor().getId().equals(currentUser.getId())) {
            throw UnauthorizedAccessException.forDelete();
        }

        recipeRepository.delete(recipe);
    }

    @Override
    public List<RecipeResponseDTO> findAll() {
        User currentUser = AuthUtil.getCurrentUserOrNull();
        return RecipeMapper.toDTOList(recipeRepository.findAll(), currentUser);
    }

    @Override
    public RecipeResponseDTO findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        User currentUser = AuthUtil.getCurrentUserOrNull();
        return RecipeMapper.toDTO(recipe, currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findByAuthorId(Long authorId) {
        User currentUser = AuthUtil.getCurrentUserOrNull();
        return RecipeMapper.toDTOList(recipeRepository.findByAuthorId(authorId), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findByTitle(String title) {
        User currentUser = AuthUtil.getCurrentUserOrNull();
        return RecipeMapper.toDTOList(recipeRepository.findByTitleContainingIgnoreCase(title), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findByIngredient(String ingredient) {
        User currentUser = AuthUtil.getCurrentUserOrNull();
        return RecipeMapper.toDTOList(recipeRepository.findByIngredientsContainingIgnoreCase(ingredient), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findLastRecipes() {
        User currentUser = AuthUtil.getCurrentUserOrNull();
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));
        return RecipeMapper.toDTOList(recipeRepository.findAll(pageable).getContent(), currentUser);
    }

    @Override
    public List<RecipeResponseDTO> findTopLikedRecipes() {
        User currentUser = AuthUtil.getCurrentUserOrNull();
        Pageable pageable = PageRequest.of(0, 5);
        return RecipeMapper.toDTOList(recipeRepository.findTopLiked(pageable), currentUser);
    }
}