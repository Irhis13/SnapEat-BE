package com.dam.web_cocina.repository;

import com.dam.web_cocina.entity.Like;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserAndRecipe(User user, Recipe recipe);

    void deleteByUserAndRecipe(User user, Recipe recipe);

    long countByRecipeId(Long recipeId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Like f WHERE f.recipe.id = :recipeId")
    void deleteByRecipeId(Long recipeId);
}
