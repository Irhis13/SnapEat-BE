package com.dam.web_cocina.repository;

import com.dam.web_cocina.entity.Favorite;
import com.dam.web_cocina.entity.Recipe;
import com.dam.web_cocina.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndRecipe(User user, Recipe recipe);

    void deleteByUserAndRecipe(User user, Recipe recipe);

    List<Favorite> findByUser(User user);
}
