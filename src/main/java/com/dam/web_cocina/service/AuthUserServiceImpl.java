package com.dam.web_cocina.service;

import com.dam.web_cocina.common.exceptions.UserNotFoundException;
import com.dam.web_cocina.entity.User;
import com.dam.web_cocina.repository.UserRepository;
import com.dam.web_cocina.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthUserServiceImpl implements IAuthUserService {

    private final UserRepository userRepository;

    public AuthUserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(email));
        return new CustomUserDetails(user);
    }
}
