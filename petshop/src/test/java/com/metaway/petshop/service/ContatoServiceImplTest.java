package com.metaway.petshop.service;

import com.metaway.petshop.dto.ContatoDTO;
import com.metaway.petshop.entities.Contato;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.ContatoRepository;
import com.metaway.petshop.services.impl.ContatoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.mockito.Mockito.*;

class ContatoServiceImplTest {

    @Mock
    private ContatoRepository contatoRepository;

    @InjectMocks
    private ContatoServiceImpl contatoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_NonExistingContato_ThrowsResourceNotFoundException() {
        // Arrange
        UUID contatoUuid = UUID.randomUUID();
        when(contatoRepository.findById(contatoUuid)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> contatoService.findById(contatoUuid));
        verify(contatoRepository, times(1)).findById(contatoUuid);
    }

    @Test
    void update_NonExistingContato_ThrowsResourceNotFoundException() {
        // Arrange
        UUID contatoUuid = UUID.randomUUID();
        ContatoDTO contatoDTO = new ContatoDTO(contatoUuid, "Tag", "email@example.com", "123456789", "Valor", null);
        when(contatoRepository.findById(contatoUuid)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> contatoService.update(contatoUuid, contatoDTO));
        verify(contatoRepository, times(1)).findById(contatoUuid);
        verify(contatoRepository, times(0)).save(any(Contato.class));
    }

    @Test
    void delete_ExistingContato_DeletesContato() {
        // Arrange
        UUID contatoUuid = UUID.randomUUID();
        when(contatoRepository.existsById(contatoUuid)).thenReturn(true);

        // Act
        contatoService.delete(contatoUuid);

        // Assert
        verify(contatoRepository, times(1)).existsById(contatoUuid);
        verify(contatoRepository, times(1)).deleteById(contatoUuid);
    }

    @Test
    void delete_NonExistingContato_ThrowsResourceNotFoundException() {
        // Arrange
        UUID contatoUuid = UUID.randomUUID();
        when(contatoRepository.existsById(contatoUuid)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> contatoService.delete(contatoUuid));
        verify(contatoRepository, times(1)).existsById(contatoUuid);
        verify(contatoRepository, times(0)).deleteById(contatoUuid);
    }

    @Test
    void delete_ExistingContatoWithDataIntegrityViolation_ThrowsDatabaseException() {
        // Arrange
        UUID contatoUuid = UUID.randomUUID();
        when(contatoRepository.existsById(contatoUuid)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(contatoRepository).deleteById(contatoUuid);

        // Act & Assert
        Assertions.assertThrows(DatabaseException.class, () -> contatoService.delete(contatoUuid));
        verify(contatoRepository, times(1)).existsById(contatoUuid);
        verify(contatoRepository, times(1)).deleteById(contatoUuid);
    }
}
