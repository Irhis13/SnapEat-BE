package com.dam.web_cocina.service;

public interface ILikeService {

    void like(Long recipeId);

    void unlike(Long recipeId);

    boolean isLiked(Long recipeId);

    long countLikes(Long recipeId);
}

