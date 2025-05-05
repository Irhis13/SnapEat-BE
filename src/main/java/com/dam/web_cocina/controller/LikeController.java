package com.dam.web_cocina.controller;

import com.dam.web_cocina.service.ILikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "*")
@PreAuthorize("isAuthenticated()")
public class LikeController {

    private final ILikeService likeService;

    public LikeController(ILikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{recipeId}")
    public ResponseEntity<Map<String, String>> likeRecipe(@PathVariable Long recipeId) {
        likeService.like(recipeId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Receta marcada como me gusta"));
    }

    @DeleteMapping("/{recipeId}")
    public ResponseEntity<Map<String, String>> unlikeRecipe(@PathVariable Long recipeId) {
        likeService.unlike(recipeId);
        return ResponseEntity.ok(Collections.singletonMap("message", "Me gusta eliminado"));
    }

    @GetMapping("/count")
    public long countLikes(@RequestParam Long recipeId) {
        return likeService.countLikes(recipeId);
    }

    @GetMapping("/exists/{recipeId}")
    public boolean isLiked(@PathVariable Long recipeId) {
        return likeService.isLiked(recipeId);
    }
}
