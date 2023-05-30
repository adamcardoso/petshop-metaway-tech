package com.metaway.petshop.controllers;

import com.metaway.petshop.api.impl.ClienteControllerImpl;
import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteControllerImplTest {

    @Mock
    private ClienteServiceImpl clienteService;

    @InjectMocks
    private ClienteControllerImpl clienteController;

    @BeforeEach
    void setUp() {
        try (MockedStatic<?> mockedStatic = Mockito.mockStatic(MockitoAnnotations.class)) {
            mockedStatic.when(() -> MockitoAnnotations.openMocks(this)).thenReturn(Mockito.mock(AutoCloseable.class));
            MockitoAnnotations.openMocks(this);
        }
    }

    @Test
    void findAll_ShouldReturnListOfClientes() {
        // Arrange
        List<ClienteDTO> expectedClientes = new ArrayList<>();

        expectedClientes.add(new ClienteDTO(UUID.randomUUID(),
                "John Doe",
                "12345678900",
                LocalDate.now(),
                new ArrayList<>(),
                new ArrayList<>()));
        expectedClientes.add(new ClienteDTO(UUID.randomUUID(),
                "John Camper",
                "12345678901",
                LocalDate.now(),
                new ArrayList<>(),
                new ArrayList<>()));

        when(clienteService.findAll()).thenReturn(expectedClientes);

        // Act
        ResponseEntity<List<ClienteDTO>> response = clienteController.findAll();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClientes, response.getBody());
        verify(clienteService, times(1)).findAll();
    }

    @Test
    void findById_WithValidUUID_ShouldReturnCliente() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        ClienteDTO expectedCliente = new ClienteDTO(
                UUID.randomUUID(),
                "John Doe",
                "12345678900",
                LocalDate.now(),
                new ArrayList<>(),
                new ArrayList<>()
        );


        when(clienteService.findById(uuid)).thenReturn(Optional.of(expectedCliente));

        // Act
        ResponseEntity<ClienteDTO> response = clienteController.findById(uuid);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCliente, response.getBody());
        verify(clienteService, times(1)).findById(uuid);
    }

    @Test
    void findById_WithInvalidUUID_ShouldThrowResponseStatusException() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(clienteService.findById(uuid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> clienteController.findById(uuid));
        verify(clienteService, times(1)).findById(uuid);
    }

    @Test
    void findByName_WithExistingName_ShouldReturnListOfClientes() {
        // Arrange
        String name = "John Doe";
        List<ClienteDTO> expectedClientes = new ArrayList<>();

        expectedClientes.add(new ClienteDTO(UUID.randomUUID(),
                "John Doe",
                "12345678900",
                LocalDate.now(),
                new ArrayList<>(),
                new ArrayList<>()));
        expectedClientes.add(new ClienteDTO(UUID.randomUUID(),
                "John Camper",
                "12345678901",
                LocalDate.now(),
                new ArrayList<>(),
                new ArrayList<>()));

        when(clienteService.findByName(name)).thenReturn(expectedClientes);

        // Act
        ResponseEntity<List<ClienteDTO>> response = clienteController.findByName(name);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedClientes, response.getBody());
        verify(clienteService, times(1)).findByName(name);
    }

    @Test
    void findByName_WithNonExistingName_ShouldReturnNotFound() {
        // Arrange
        String name = "Nonexistent";

        when(clienteService.findByName(name)).thenThrow(ResourceNotFoundException.class);

        // Act
        ResponseEntity<List<ClienteDTO>> response = clienteController.findByName(name);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(clienteService, times(1)).findByName(name);
    }

    @Test
    void delete_WithValidUUID_ShouldReturnNoContent() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        // Act
        ResponseEntity<Void> response = clienteController.delete(uuid);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clienteService, times(1)).delete(uuid);
    }

    @Test
    void delete_WithInvalidUUID_ShouldReturnNotFound() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        doThrow(ResourceNotFoundException.class).when(clienteService).delete(uuid);

        // Act
        ResponseEntity<Void> response = clienteController.delete(uuid);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(clienteService, times(1)).delete(uuid);
    }
}
