package com.dam.web_cocina.controller;

import com.dam.web_cocina.dto.RecipeDTO;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.service.IRecipeService;
import com.dam.web_cocina.service.RecipeServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    private final IRecipeService recipeService;

    public RecipeController(RecipeServiceImpl recipeServiceImpl) {
        this.recipeService = recipeServiceImpl;
    }

    @PostMapping
    public RecipeResponseDTO createRecipe(@RequestBody RecipeDTO dto) {
        return recipeService.save(dto);
    }

    @PutMapping("/{id}")
    public RecipeResponseDTO updateRecipe(@PathVariable Long id, @RequestBody RecipeDTO dto) {
        dto.setId(id);
        return recipeService.save(dto);
    }

    @DeleteMapping("/{id}")
    public void deleteRecipe(@PathVariable Long id) {
        recipeService.delete(id);
    }

    @GetMapping
    public List<RecipeResponseDTO> listRecipes() {
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public RecipeResponseDTO getRecipeById(@PathVariable Long id) {
        return recipeService.findById(id);
    }

    @GetMapping("/title")
    public List<RecipeResponseDTO> getByTitle(@RequestParam String value) {
        return recipeService.findByTitle(value);
    }

    @GetMapping("/ingredient")
    public List<RecipeResponseDTO> getByIngredient(@RequestParam String value) {
        return recipeService.findByIngredient(value);
    }

    @GetMapping("/author/{authorId}")
    public List<RecipeResponseDTO> getByAuthor(@PathVariable Long authorId) {
        return recipeService.findByAuthorId(authorId);
    }

    @GetMapping("/latest")
    public List<RecipeResponseDTO> getLastRecipes() {
        return recipeService.findLastRecipes();
    }
}
