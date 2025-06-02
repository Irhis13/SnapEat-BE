package com.dam.web_cocina.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true)
    private String email;

    @Column(length = 60)
    private String name;

    @Column(length = 100)
    private String surname;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    @Column(name = "profile_image")
    private String profileImage;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
