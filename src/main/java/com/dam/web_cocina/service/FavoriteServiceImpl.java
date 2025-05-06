package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.RecipeNotFoundException;
import com.dam.web_cocina.common.utils.AuthUtil;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.entity.Favorite;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.mapper.RecipeMapper;
import com.dam.web_cocina.repository.FavoriteRepository;
import com.dam.web_cocina.repository.RecipeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements IFavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final RecipeRepository recipeRepository;
    private final AuthUtil authUtil;

    public FavoriteServiceImpl(
            FavoriteRepository favoriteRepository,
            RecipeRepository recipeRepository,
            AuthUtil authUtil)
    {
        this.favoriteRepository = favoriteRepository;
        this.recipeRepository = recipeRepository;
        this.authUtil = authUtil;
    }

    @Override
    public void favorite(Long recipeId) {
        User user = authUtil.getCurrentUser();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
        if (!favoriteRepository.existsByUserAndRecipe(user, recipe)) {
            favoriteRepository.save(new Favorite(null, user, recipe, LocalDateTime.now()));
        }
    }

    @Override
    public void unfavorite(Long recipeId) {
        User user = authUtil.getCurrentUser();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
        favoriteRepository.deleteByUserAndRecipe(user, recipe);
    }

    @Override
    public boolean isFavorite(Long recipeId) {
        User user = authUtil.getCurrentUser();
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException(recipeId));
        return favoriteRepository.existsByUserAndRecipe(user, recipe);
    }

    @Override
    public List<RecipeResponseDTO> getFavoritesByCurrentUser() {
        User user = authUtil.getCurrentUser();
        List<Favorite> favorites = favoriteRepository.findByUser(user);
        return favorites.stream()
                .map(Favorite::getRecipe)
                .map(recipe -> RecipeMapper.toDTO(recipe, user))
                .toList();
    }
}
