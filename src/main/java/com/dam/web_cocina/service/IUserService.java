package com.dam.web_cocina.service;

import com.dam.web_cocina.dto.UserDTO;
import com.dam.web_cocina.dto.UserResponseDTO;
import com.dam.web_cocina.entity.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<User> findAll();

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    User save(User user);

    void delete(Long id);

    User register(UserDTO dto);

    UserResponseDTO updateUser(Long id, UserDTO dto);

    List<UserResponseDTO> findAllDTO();

    UserResponseDTO getUserDetailsById(Long id);
}
