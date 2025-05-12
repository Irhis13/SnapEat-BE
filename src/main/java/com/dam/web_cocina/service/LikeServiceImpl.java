package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.RecipeNotFoundException;
import com.dam.web_cocina.common.exceptions.UnauthorizedAccessException;
import com.dam.web_cocina.common.utils.AuthUtil;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.entity.Like;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.mapper.RecipeMapper;
import com.dam.web_cocina.repository.FavoriteRepository;
import com.dam.web_cocina.repository.LikeRepository;
import com.dam.web_cocina.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class LikeServiceImpl implements ILikeService {

    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;
    private final FavoriteRepository favoriteRepository;

    public LikeServiceImpl(LikeRepository likeRepository, RecipeRepository recipeRepository, FavoriteRepository favoriteRepository) {
        this.likeRepository = likeRepository;
        this.recipeRepository = recipeRepository;
        this.favoriteRepository = favoriteRepository;
    }

    @Override
    public RecipeResponseDTO like(Long recipeId) {
        User user = AuthUtil.getCurrentUser();
        if (user == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));

        if (!likeRepository.existsByUserAndRecipe(user, recipe)) {
            Like like = new Like();
            like.setUser(user);
            like.setRecipe(recipe);
            like.setLikedAt(LocalDateTime.now());
            likeRepository.save(like);
        }

        return findById(recipeId);
    }

    @Override
    public void unlike(Long recipeId) {
        User user = AuthUtil.getCurrentUser();
        if (user == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }
        likeRepository.deleteByUserAndRecipe(user, getRecipe(recipeId));
    }

    @Override
    public RecipeResponseDTO findById(Long id) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException(id));

        User currentUser = AuthUtil.getCurrentUserOrNull();
        RecipeResponseDTO dto = RecipeMapper.toDTO(recipe);

        if (currentUser != null) {
            dto.setLikedByCurrentUser(
                    likeRepository.existsByUserAndRecipe(currentUser, recipe)
            );
            dto.setFavoritedByCurrentUser(
                    favoriteRepository.existsByUserAndRecipe(currentUser, recipe)
            );
            dto.setOwner(recipe.getAuthor().getId().equals(currentUser.getId()));
        } else {
            dto.setLikedByCurrentUser(false);
            dto.setFavoritedByCurrentUser(false);
            dto.setOwner(false);
        }

        return dto;
    }

    @Override
    public boolean isLiked(Long recipeId) {
        User user = AuthUtil.getCurrentUser();
        if (user == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }
        return likeRepository.existsByUserAndRecipe(user, getRecipe(recipeId));
    }

    @Override
    public long countLikes(Long recipeId) {
        return likeRepository.countByRecipeId(recipeId);
    }

    private Recipe getRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
    }
}
