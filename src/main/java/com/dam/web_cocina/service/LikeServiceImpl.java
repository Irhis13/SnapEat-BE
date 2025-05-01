package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.RecipeNotFoundException;
import com.dam.web_cocina.common.utils.AuthUtil;
import com.dam.web_cocina.entity.Like;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
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
    private final AuthUtil authUtil;

    public LikeServiceImpl(LikeRepository likeRepository, RecipeRepository recipeRepository, AuthUtil authUtil) {
        this.likeRepository = likeRepository;
        this.recipeRepository = recipeRepository;
        this.authUtil = authUtil;
    }

    @Override
    public void like(Long recipeId) {
        User user = authUtil.getCurrentUser();

        if (!likeRepository.existsByUserAndRecipe(user, getRecipe(recipeId))) {
            likeRepository.save(new Like(null, user, getRecipe(recipeId), LocalDateTime.now()));
        }
    }

    @Override
    public void unlike(Long recipeId) {
        User user = authUtil.getCurrentUser();
        likeRepository.deleteByUserAndRecipe(user, getRecipe(recipeId));
    }

    @Override
    public boolean isLiked(Long recipeId) {
        User user = authUtil.getCurrentUser();
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
