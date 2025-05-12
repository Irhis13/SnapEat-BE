package com.dam.web_cocina.dto;

import com.dam.web_cocina.entity.Category;
import lombok.Data;

import java.util.List;

@Data
public class RecipeResponseDTO {
    private Long id;
    private String hashedId;
    private String title;
    private String description;
    private Category category;
    private String ingredients;
    private List<String> steps;
    private String imageUrl;
    private String authorName;
    private Long authorId;
    private int likes;
    private boolean likedByCurrentUser;
    private boolean owner;
    private boolean favoritedByCurrentUser;
}
