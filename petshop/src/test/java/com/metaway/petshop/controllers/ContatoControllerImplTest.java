package com.metaway.petshop.controllers;

import com.metaway.petshop.api.impl.ContatoControllerImpl;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.impl.ContatoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ContatoControllerImplTest {

    @Mock
    private ContatoServiceImpl contatoService;

    @InjectMocks
    private ContatoControllerImpl contatoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void delete_WithValidUUID_ShouldReturnNoContent() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        ResponseEntity<Void> response = contatoController.delete(uuid);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(contatoService, times(1)).delete(uuid);
    }

    @Test
    void delete_WithInvalidUUID_ShouldReturnNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        doThrow(ResourceNotFoundException.class).when(contatoService).delete(uuid);

        // Act
        ResponseEntity<Void> response = contatoController.delete(uuid);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(contatoService, times(1)).delete(uuid);
    }
}
