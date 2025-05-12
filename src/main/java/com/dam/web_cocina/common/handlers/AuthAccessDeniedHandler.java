package com.dam.web_cocina.common.handlers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class AuthAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException accessDeniedException
    ) {
        try {
            response.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED,
                    accessDeniedException.getMessage()
            );
        } catch (IOException e) {
            throw new RuntimeException("Error al enviar respuesta de acceso denegado", e);
        }
    }
}
