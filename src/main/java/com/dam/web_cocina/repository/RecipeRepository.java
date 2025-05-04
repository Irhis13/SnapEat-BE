package com.dam.web_cocina.repository;

import com.dam.web_cocina.entity.Recipe;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    // Buscar recetas por ID del autor
    List<Recipe> findByAuthorId(Long authorId);

    List<Recipe> findByTitleContainingIgnoreCase(String title);

    List<Recipe> findByIngredientsContainingIgnoreCase(String ingredient);

    @Query("SELECT r FROM Recipe r LEFT JOIN r.likes l GROUP BY r.id ORDER BY COUNT(l) DESC")
    List<Recipe> findTopLiked(Pageable pageable);
}
