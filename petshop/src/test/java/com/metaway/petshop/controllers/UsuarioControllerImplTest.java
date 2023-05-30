package com.metaway.petshop.controllers;

import com.metaway.petshop.api.impl.UsuarioControllerImpl;
import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UsuarioControllerImplTest {

    @Mock
    private UsuarioServiceImpl usuarioService;

    @InjectMocks
    private UsuarioControllerImpl usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void update_WithValidUUIDAndDto_ShouldReturnUpdatedUsuario() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO("John Doe", null);
        UsuarioDTO expectedUsuario = new UsuarioDTO(uuid, "john.doe@example.com", "John Doe", null, null);

        when(usuarioService.update(uuid, dto)).thenReturn(expectedUsuario);

        // Act
        ResponseEntity<UsuarioDTO> response = usuarioController.update(uuid, dto);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedUsuario, response.getBody());
        verify(usuarioService, times(1)).update(uuid, dto);
    }

    @Test
    void update_WithInvalidUUID_ShouldReturnNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        UsuarioUpdateDTO dto = new UsuarioUpdateDTO("John Doe", null);

        when(usuarioService.update(uuid, dto)).thenThrow(ResourceNotFoundException.class);

        // Act
        ResponseEntity<UsuarioDTO> response = usuarioController.update(uuid, dto);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).update(uuid, dto);
    }

    @Test
    void delete_WithValidUUID_ShouldReturnNoContent() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        ResponseEntity<Void> response = usuarioController.delete(uuid);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(usuarioService, times(1)).delete(uuid);
    }

    @Test
    void delete_WithInvalidUUID_ShouldReturnNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        doThrow(ResourceNotFoundException.class).when(usuarioService).delete(uuid);

        // Act
        ResponseEntity<Void> response = usuarioController.delete(uuid);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioService, times(1)).delete(uuid);
    }
}
