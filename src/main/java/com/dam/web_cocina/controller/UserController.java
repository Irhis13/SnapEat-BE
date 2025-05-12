package com.dam.web_cocina.controller;

import com.dam.web_cocina.common.utils.HashUtil;
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

    @DeleteMapping("/{hashedId}")
    public void deleteUser(@PathVariable String hashedId) {
        Long id = HashUtil.decode(hashedId);
        userService.delete(id);
    }

    @PostMapping("/register")
    public User registerUser(@RequestBody UserDTO dto) {
        return userService.register(dto);
    }

    @PutMapping("/{hashedId}")
    public UserResponseDTO updateUser(@PathVariable String hashedId, @RequestBody UserDTO dto) {
        Long id = HashUtil.decode(hashedId);
        return userService.updateUser(id, dto);
    }

    @GetMapping
    public List<UserResponseDTO> listUsers() {
        return userService.findAllDTO();
    }

    @GetMapping("/{hashedId}")
    public UserResponseDTO getUserById(@PathVariable String hashedId) {
        Long id = HashUtil.decode(hashedId);
        return userService.getUserDetailsById(id);
    }
}
