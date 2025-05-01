package com.dam.web_cocina.controller;

import com.dam.web_cocina.common.exceptions.InvalidCredentialsException;
import com.dam.web_cocina.dto.LoginDTO;
import com.dam.web_cocina.dto.LoginResponseDTO;
import com.dam.web_cocina.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginDTO dto) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )
            );
        } catch (Exception ex) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(dto.getEmail());
        return new LoginResponseDTO(token);
    }
}
