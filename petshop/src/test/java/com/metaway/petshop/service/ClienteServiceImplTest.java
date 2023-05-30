package com.metaway.petshop.service;

import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.repositories.ClienteRepository;
import com.metaway.petshop.services.impl.ClienteServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.*;

class ClienteServiceImplTest {
    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteServiceImpl clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ValidId_ReturnsClienteDTO() {
        // Arrange
        UUID clienteUuid = UUID.randomUUID();
        Cliente cliente = new Cliente(clienteUuid, "John Doe", "123456789", LocalDate.now(), new ArrayList<>(), new ArrayList<>());
        when(clienteRepository.findById(clienteUuid)).thenReturn(Optional.of(cliente));

        // Act
        Optional<ClienteDTO> result = clienteService.findById(clienteUuid);

        // Assert
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(clienteUuid, result.get().clienteUuid());
        Assertions.assertEquals("John Doe", result.get().nomeDoCliente());
        Assertions.assertEquals("123456789", result.get().cpf());
        Assertions.assertEquals(LocalDate.now(), result.get().dataDeCadastro());
        Assertions.assertTrue(result.get().enderecos().isEmpty());
        Assertions.assertTrue(result.get().pets().isEmpty());

        verify(clienteRepository, times(1)).findById(clienteUuid);
    }

    @Test
    void findById_InvalidId_ReturnsEmptyOptional() {
        // Arrange
        UUID clienteUuid = UUID.randomUUID();
        when(clienteRepository.findById(clienteUuid)).thenReturn(Optional.empty());

        // Act
        Optional<ClienteDTO> result = clienteService.findById(clienteUuid);

        // Assert
        Assertions.assertFalse(result.isPresent());

        verify(clienteRepository, times(1)).findById(clienteUuid);
    }
}
