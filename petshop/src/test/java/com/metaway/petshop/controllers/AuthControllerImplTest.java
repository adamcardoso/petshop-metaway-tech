package com.metaway.petshop.controllers;

import com.metaway.petshop.api.impl.AuthControllerImpl;
import com.metaway.petshop.config.security.TokenServiceImpl;
import com.metaway.petshop.dto.LoginDTO;
import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.entities.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthControllerImplTest {

    private AuthControllerImpl authController;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenServiceImpl tokenServiceImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthControllerImpl(authenticationManager, tokenServiceImpl);
    }

    @Test
    void login_WithInvalidCredentials_ReturnsUnauthorized() {
        // Arrange
        LoginDTO loginDTO = new LoginDTO("username", "password");
        doThrow(BadCredentialsException.class).when(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));

        // Act
        ResponseEntity<UsuarioDTO> response = authController.login(loginDTO);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void logout_WithValidToken_ReturnsNoContent() {
        // Arrange
        String token = "token";

        // Act
        ResponseEntity<Void> response = authController.logout(token);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(tokenServiceImpl, times(1)).invalidarToken(token);
    }

    @Test
    void logout_WithException_ReturnsInternalServerError() {
        // Arrange
        String token = "token";
        doThrow(RuntimeException.class).when(tokenServiceImpl).invalidarToken(token);

        // Act
        ResponseEntity<Void> response = authController.logout(token);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
