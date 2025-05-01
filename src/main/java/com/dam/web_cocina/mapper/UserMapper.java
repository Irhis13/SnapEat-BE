package com.dam.web_cocina.mapper;

import com.dam.web_cocina.dto.UserResponseDTO;
import com.dam.web_cocina.entity.User;

import java.util.List;

public class UserMapper {

    private UserMapper() {
        // Private constructor
    }

    public static UserResponseDTO toDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    public static List<UserResponseDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
