package com.dam.web_cocina.controller;

import com.dam.web_cocina.dto.UserDTO;
import com.dam.web_cocina.dto.UserResponseDTO;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.service.IUserService;
import com.dam.web_cocina.service.UserServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final IUserService userService;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userService = userServiceImpl;
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.delete(id);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDTO dto) {
        return userService.register(dto);
    }

    @GetMapping
    public List<UserResponseDTO> listUsers() {
        return userService.findAllDTO();
    }

    @GetMapping("/{id}")
    public UserResponseDTO getUserById(@PathVariable Long id) {
        return userService.findByIdDTO(id);
    }
}
