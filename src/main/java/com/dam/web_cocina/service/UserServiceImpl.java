package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.*;
import com.dam.web_cocina.common.utils.AuthUtil;
import com.dam.web_cocina.dto.UserProfileDTO;
import com.dam.web_cocina.entity.Genero;
import com.dam.web_cocina.entity.Role;
import com.dam.web_cocina.dto.UserDTO;
import com.dam.web_cocina.dto.UserResponseDTO;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.entity.UserAvatar;
import com.dam.web_cocina.mapper.UserMapper;
import com.dam.web_cocina.repository.UserAvatarRepository;
import com.dam.web_cocina.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private final UserRepository userRepository;
    private final UserAvatarRepository userAvatarRepository;
    private final PasswordEncoder passwordEncoder;
    private final IImageService imageService;

    public UserServiceImpl(UserRepository userRepository, UserAvatarRepository userAvatarRepository, PasswordEncoder passwordEncoder, IImageService imageService) {
        this.userRepository = userRepository;
        this.userAvatarRepository = userAvatarRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuario", "ID", id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public User register(UserDTO dto) {
        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailUsedException(dto.getEmail());
        }

        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new UsernameUsedException(dto.getUsername());
        }

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());

        if (dto.getGenero() != null && !dto.getGenero().isBlank()) {
            try {
                user.setGenero(Genero.valueOf(dto.getGenero().toUpperCase()));
            } catch (IllegalArgumentException ex) {
                throw new IllegalArgumentException("Género no válido: " + dto.getGenero());
            }
        } else {
            user.setGenero(null);
        }

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER);

        return userRepository.save(user);
    }

    public UserResponseDTO updateUser(Long id, UserDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", "ID", id));

        user.setUsername(dto.getUsername());
        if (!user.getEmail().equals(dto.getEmail())) {
            if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
                throw new EmailUsedException(dto.getEmail());
            }
            user.setEmail(dto.getEmail());
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        return UserMapper.toDTO(userRepository.save(user));
    }

    @Override
    public List<UserResponseDTO> findAllDTO() {
        return UserMapper.toDTOList(userRepository.findAll());
    }

    @Override
    public UserResponseDTO getUserDetailsById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuario", "ID", id));
    }

    @Override
    public UserResponseDTO updateProfileWithImage(UserProfileDTO dto, MultipartFile imagen) {
        User currentUser = AuthUtil.getCurrentUser();

        if (currentUser == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }

        if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
            if (!dto.getUsername().equals(currentUser.getUsername())
                    && userRepository.findByUsername(dto.getUsername()).isPresent()) {
                throw new UsernameUsedException(dto.getUsername());
            }
            currentUser.setUsername(dto.getUsername());
        }

        if (dto.getName() != null)
            currentUser.setName(dto.getName());

        if (dto.getSurname() != null)
            currentUser.setSurname(dto.getSurname());

        if (dto.getGenero() != null && !dto.getGenero().isBlank()) {
            try {
                currentUser.setGenero(Genero.valueOf(dto.getGenero()));
            } catch (IllegalArgumentException ex) {
                throw new InvalidGeneroException(dto.getGenero());
            }
        }

        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            currentUser.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (imagen != null && !imagen.isEmpty()) {
            String imageUrl = imageService.saveImage(imagen);
            currentUser.setProfileImage(imageUrl);

            boolean alreadyExists = userAvatarRepository
                    .findByUserId(currentUser.getId())
                    .stream()
                    .anyMatch(ua -> ua.getImageUrl().equals(imageUrl));

            if (!alreadyExists) {
                UserAvatar avatar = new UserAvatar();
                avatar.setImageUrl(imageUrl);
                avatar.setUser(currentUser);
                userAvatarRepository.save(avatar);
            }
        } else if (dto.getImageUrl() != null && !dto.getImageUrl().isBlank()) {
            currentUser.setProfileImage(dto.getImageUrl());
        }

        return UserMapper.toDTO(userRepository.save(currentUser));
    }

    @Override
    public List<String> getUserAvatars() {
        User currentUser = AuthUtil.getCurrentUser();

        if (currentUser == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }

        return userAvatarRepository.findByUserId(currentUser.getId())
                .stream()
                .map(UserAvatar::getImageUrl)
                .toList();
    }

    @Override
    public UserResponseDTO getCurrentUserProfile() {
        User currentUser = AuthUtil.getCurrentUser();

        if (currentUser == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }

        return UserMapper.toDTO(currentUser);
    }

    @Override
    public void deleteUserAvatar(String imageUrl) {
        User currentUser = AuthUtil.getCurrentUser();
        if (currentUser == null) {
            throw UnauthorizedAccessException.forNotAuthenticated();
        }

        List<UserAvatar> avatars = userAvatarRepository.findByUserId(currentUser.getId());

        UserAvatar toDelete = avatars.stream()
                .filter(ua -> ua.getImageUrl().equals(imageUrl))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Avatar", "URL", imageUrl));

        if (imageUrl.equals(currentUser.getProfileImage())) {
            throw new IllegalStateException("No se puede eliminar el avatar actualmente en uso.");
        }

        userAvatarRepository.delete(toDelete);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        User currentUser = AuthUtil.getCurrentUser();

        if (currentUser != null && currentUser.getUsername().equals(username)) {
            return true;
        }

        return userRepository.findByUsername(username).isEmpty();
    }
}
