package com.dam.web_cocina.controller;

import com.dam.web_cocina.common.utils.HashUtil;
import com.dam.web_cocina.dto.LoginResponseDTO;
import com.dam.web_cocina.dto.UserDTO;
import com.dam.web_cocina.dto.UserProfileDTO;
import com.dam.web_cocina.dto.UserResponseDTO;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.security.JwtUtil;
import com.dam.web_cocina.service.IUserService;
import com.dam.web_cocina.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final IUserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserServiceImpl userServiceImpl, JwtUtil jwtUtil) {
        this.userService = userServiceImpl;
        this.jwtUtil = jwtUtil;
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
    public LoginResponseDTO registerUser(@RequestBody @Valid UserDTO dto) {
        userService.register(dto);
        String token = jwtUtil.generateToken(dto.getEmail());
        return new LoginResponseDTO(token);
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

    @PutMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("isAuthenticated()")
    public UserResponseDTO updateUserProfile(
            @RequestPart("perfil") @Valid UserProfileDTO perfilDTO,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen
    ) {
        return userService.updateProfileWithImage(perfilDTO, imagen);
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public UserResponseDTO getCurrentUserProfile() {
        return userService.getCurrentUserProfile();
    }

    @GetMapping("/me/avatars")
    @PreAuthorize("isAuthenticated()")
    public List<String> getUserAvatars() {
        return userService.getUserAvatars();
    }

    @DeleteMapping("/me/avatars")
    @PreAuthorize("isAuthenticated()")
    public void deleteUserAvatarFromMe(@RequestParam String imageUrl) {
        userService.deleteUserAvatar(imageUrl);
    }

    @GetMapping("/check-username")
    @PreAuthorize("isAuthenticated()")
    public boolean isUsernameAvailable(@RequestParam String username) {
        return userService.isUsernameAvailable(username);
    }
}
