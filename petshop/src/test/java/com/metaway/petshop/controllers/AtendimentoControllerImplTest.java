package com.metaway.petshop.controllers;

import com.metaway.petshop.api.impl.AtendimentoControllerImpl;
import com.metaway.petshop.dto.AtendimentoDTO;
import com.metaway.petshop.entities.Atendimento;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.interfaces.AtendimentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class AtendimentoControllerImplTest {

    private AtendimentoControllerImpl atendimentoController;

    @Mock
    private AtendimentoService atendimentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        atendimentoController = new AtendimentoControllerImpl(atendimentoService);
    }

    @Test
    void findAll_ReturnsListOfAtendimentos() {
        // Arrange
        List<AtendimentoDTO> atendimentos = Collections.singletonList(new AtendimentoDTO(new Atendimento()));
        when(atendimentoService.findAll()).thenReturn(atendimentos);

        // Act
        ResponseEntity<List<AtendimentoDTO>> response = atendimentoController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(atendimentos, response.getBody());
    }

    @Test
    void findById_WithExistingId_ReturnsAtendimento() {
        // Arrange
        UUID id = UUID.randomUUID();
        AtendimentoDTO atendimento = new AtendimentoDTO(new Atendimento());
        when(atendimentoService.findById(id)).thenReturn(atendimento);

        // Act
        ResponseEntity<AtendimentoDTO> response = atendimentoController.findById(id);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(atendimento, response.getBody());
    }

    @Test
    void findById_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(atendimentoService.findById(id)).thenThrow(new ResourceNotFoundException("Atendimento not found: " + id));

        // Act
        ResponseEntity<AtendimentoDTO> response = atendimentoController.findById(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void insert_ValidAtendimento_ReturnsCreated() {
        // Arrange
        AtendimentoDTO atendimento = new AtendimentoDTO(new Atendimento());
        when(atendimentoService.insert(any(AtendimentoDTO.class))).thenReturn(atendimento);

        // Act
        ResponseEntity<AtendimentoDTO> response = atendimentoController.insert(atendimento);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(atendimento, response.getBody());
        assertEquals(URI.create("/api/atendimentos/" + atendimento.atendimentoUuid()), response.getHeaders().getLocation());
    }

    @Test
    void update_WithExistingIdAndValidAtendimento_ReturnsUpdatedAtendimento() {
        // Arrange
        UUID id = UUID.randomUUID();
        AtendimentoDTO atendimento = new AtendimentoDTO(new Atendimento());
        when(atendimentoService.update(eq(id), any(AtendimentoDTO.class))).thenReturn(atendimento);

        // Act
        ResponseEntity<AtendimentoDTO> response = atendimentoController.update(id, atendimento);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(atendimento, response.getBody());
    }

    @Test
    void update_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(atendimentoService.update(eq(id), any(AtendimentoDTO.class))).thenThrow(new ResourceNotFoundException("Atendimento not found: " + id));

        // Act
        ResponseEntity<AtendimentoDTO> response = atendimentoController.update(id, new AtendimentoDTO(new Atendimento()));

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete_WithExistingId_ReturnsNoContent() {
        // Arrange
        UUID id = UUID.randomUUID();

        // Act
        ResponseEntity<Void> response = atendimentoController.delete(id);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(atendimentoService, times(1)).delete(id);
    }

    @Test
    void delete_WithNonExistingId_ReturnsNotFound() {
        // Arrange
        UUID id = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("Atendimento not found: " + id)).when(atendimentoService).delete(id);

        // Act
        ResponseEntity<Void> response = atendimentoController.delete(id);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void delete_WithDatabaseException_ReturnsInternalServerError() {
        // Arrange
        UUID id = UUID.randomUUID();
        doThrow(new DatabaseException("Error deleting atendimento: " + id)).when(atendimentoService).delete(id);

        // Act
        ResponseEntity<Void> response = atendimentoController.delete(id);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
