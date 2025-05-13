package com.dam.web_cocina.repository;

import com.dam.web_cocina.entity.Favorite;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndRecipe(User user, Recipe recipe);

    void deleteByUserAndRecipe(User user, Recipe recipe);

    List<Favorite> findByUser(User user);

    @Modifying
    @Transactional
    @Query("DELETE FROM Favorite f WHERE f.recipe.id = :recipeId")
    void deleteByRecipeId(Long recipeId);
}
