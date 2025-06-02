package com.dam.web_cocina.mapper;

import com.dam.web_cocina.common.utils.HashUtil;
import com.dam.web_cocina.dto.UserResponseDTO;
import com.dam.web_cocina.entity.User;

import java.util.List;

public class UserMapper {

    private UserMapper() {
        // Private constructor
    }

    public static UserResponseDTO toDTO(User user) {
        String hashedId = HashUtil.encode(user.getId());

        return new UserResponseDTO(
                user.getId(),
                hashedId,
                user.getUsername(),
                user.getName(),
                user.getSurname(),
                user.getGenero() != null ? user.getGenero().name() : null,
                user.getEmail(),
                user.getProfileImage()
        );
    }


    public static List<UserResponseDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(UserMapper::toDTO)
                .toList();
    }
}
