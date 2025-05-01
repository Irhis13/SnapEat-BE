package com.dam.web_cocina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 1000)
    private String description;

    private String ingredients;

    @ElementCollection
    @CollectionTable(name = "recipe_steps", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "step")
    private List<String> steps;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
}
