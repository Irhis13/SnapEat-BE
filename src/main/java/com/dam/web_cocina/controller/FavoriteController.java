package com.dam.web_cocina.controller;

import com.dam.web_cocina.common.utils.HashUtil;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.service.IFavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
@PreAuthorize("isAuthenticated()")
public class FavoriteController {

    private final IFavoriteService favoriteService;

    public FavoriteController(IFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{hashedRecipeId}")
    public ResponseEntity<Void> favorite(@PathVariable String hashedRecipeId) {
        Long recipeId = HashUtil.decode(hashedRecipeId);
        favoriteService.favorite(recipeId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{hashedRecipeId}")
    public ResponseEntity<Void> unfavorite(@PathVariable String hashedRecipeId) {
        Long recipeId = HashUtil.decode(hashedRecipeId);
        favoriteService.unfavorite(recipeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exists/{hashedRecipeId}")
    public boolean isFavorite(@PathVariable String hashedRecipeId) {
        Long recipeId = HashUtil.decode(hashedRecipeId);
        return favoriteService.isFavorite(recipeId);
    }

    @GetMapping
    public ResponseEntity<List<RecipeResponseDTO>> getUserFavorites() {
        return ResponseEntity.ok(favoriteService.getFavoritesByCurrentUser());
    }
}
