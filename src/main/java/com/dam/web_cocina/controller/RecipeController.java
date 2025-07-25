package com.dam.web_cocina.controller;

import com.dam.web_cocina.common.annotations.IsImageFormat;
import com.dam.web_cocina.common.annotations.MaxSizeFile;
import com.dam.web_cocina.common.utils.HashUtil;
import com.dam.web_cocina.dto.RecipeDTO;
import com.dam.web_cocina.dto.RecipeResponseDTO;
import com.dam.web_cocina.service.IRecipeService;
import com.dam.web_cocina.service.RecipeServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/recipes")
@CrossOrigin(origins = "*")
public class RecipeController {

    private final IRecipeService recipeService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(),
                        error.getDefaultMessage())
        );

        return errors;
    }

    public RecipeController(RecipeServiceImpl recipeServiceImpl) {
        this.recipeService = recipeServiceImpl;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RecipeResponseDTO createRecipeMultipart(
            @Valid @RequestPart("receta") RecipeDTO receta,
            @IsImageFormat @MaxSizeFile(size = 2048)
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        return recipeService.saveWithImage(receta, imagen);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecipeResponseDTO createRecipeJson(@Valid @RequestBody RecipeDTO recipeDTO) {
        return recipeService.save(recipeDTO);
    }

    @PutMapping(value = "/{hashedId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public RecipeResponseDTO updateRecipeJson(
            @PathVariable String hashedId,
            @Valid @RequestBody RecipeDTO dto
    ) {
        dto.setId(HashUtil.decode(hashedId));
        return recipeService.save(dto);
    }

    @PutMapping(value = "/{hashedId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RecipeResponseDTO updateRecipeMultipart(
            @PathVariable String hashedId,
            @Valid @RequestPart("receta") RecipeDTO receta,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        receta.setId(HashUtil.decode(hashedId));
        return recipeService.saveWithImage(receta, imagen);
    }

    @DeleteMapping("/{hashedId}")
    public void deleteRecipe(@PathVariable String hashedId) {
        recipeService.delete(HashUtil.decode(hashedId));
    }

    @GetMapping
    public List<RecipeResponseDTO> listRecipes() {
        return recipeService.findAll();
    }

    @GetMapping("/{hashedId}")
    public RecipeResponseDTO getRecipeById(@PathVariable String hashedId) {
        return recipeService.findById(HashUtil.decode(hashedId));
    }

    @GetMapping("/title")
    public List<RecipeResponseDTO> getByTitle(@RequestParam String value) {
        return recipeService.findByTitle(value);
    }

    @GetMapping("/ingredient")
    public List<RecipeResponseDTO> getByIngredient(@RequestParam String value) {
        return recipeService.findByIngredient(value);
    }

    @GetMapping("/author/{hashedAuthorId}")
    public List<RecipeResponseDTO> getByAuthor(@PathVariable String hashedAuthorId) {
        return recipeService.findByAuthorId(HashUtil.decode(hashedAuthorId));
    }

    @GetMapping("/latest")
    public List<RecipeResponseDTO> getLastRecipes() {
        return recipeService.findLastRecipes();
    }

    @GetMapping("/top-liked")
    public List<RecipeResponseDTO> getTopLikedRecipes() {
        return recipeService.findTopLikedRecipes();
    }
}
