package com.dam.web_cocina.repository;

import com.dam.web_cocina.entity.Like;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndRecipe(User user, Recipe recipe);

    void deleteByUserAndRecipe(User user, Recipe recipe);

    long countByRecipeId(Long recipeId);
}
