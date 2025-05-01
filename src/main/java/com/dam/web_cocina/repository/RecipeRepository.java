package com.dam.web_cocina.repository;

import com.dam.web_cocina.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Buscar recetas por ID del autor
    List<Recipe> findByAuthorId(Long authorId);

    List<Recipe> findByTitleContainingIgnoreCase(String title);

    List<Recipe> findByIngredientsContainingIgnoreCase(String ingredient);
}
