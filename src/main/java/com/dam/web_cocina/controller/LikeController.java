package com.dam.web_cocina.controller;

import com.dam.web_cocina.common.utils.HashUtil;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.service.ILikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "*")
public class LikeController {

    private final ILikeService likeService;

    public LikeController(ILikeService likeService) {
        this.likeService = likeService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/{hashedRecipeId}")
    public ResponseEntity<RecipeResponseDTO> likeRecipe(@PathVariable String hashedRecipeId) {
        Long recipeId = HashUtil.decode(hashedRecipeId);
        RecipeResponseDTO dto = likeService.like(recipeId);
        return ResponseEntity.ok(dto);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{hashedRecipeId}")
    public ResponseEntity<Map<String, String>> unlikeRecipe(@PathVariable String hashedRecipeId) {
        Long recipeId = HashUtil.decode(hashedRecipeId);
        likeService.unlike(recipeId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Me gusta eliminado"));
    }

    @GetMapping("/count")
    public long countLikes(@RequestParam String hashedRecipeId) {
        Long recipeId = HashUtil.decode(hashedRecipeId);
        return likeService.countLikes(recipeId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/exists/{hashedRecipeId}")
    public boolean isLiked(@PathVariable String hashedRecipeId) {
        Long recipeId = HashUtil.decode(hashedRecipeId);
        return likeService.isLiked(recipeId);
    }
}
