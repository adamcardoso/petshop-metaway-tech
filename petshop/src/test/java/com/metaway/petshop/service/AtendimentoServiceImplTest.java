package com.metaway.petshop.service;

import com.metaway.petshop.dto.AtendimentoDTO;
import com.metaway.petshop.entities.Atendimento;
import com.metaway.petshop.entities.Pets;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.AtendimentoRepository;
import com.metaway.petshop.services.impl.AtendimentoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

import static org.mockito.Mockito.*;

class AtendimentoServiceImplTest {

    @Mock
    private AtendimentoRepository atendimentoRepository;

    @InjectMocks
    private AtendimentoServiceImpl atendimentoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_existingId_shouldReturnAtendimentoDTO() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Atendimento atendimento = new Atendimento();
        atendimento.setAtendimentoUuid(uuid);
        AtendimentoDTO expectedDTO = new AtendimentoDTO(atendimento);

        when(atendimentoRepository.findById(uuid)).thenReturn(Optional.of(atendimento));

        // Act
        AtendimentoDTO actualDTO = atendimentoService.findById(uuid);

        // Assert
        Assertions.assertEquals(expectedDTO, actualDTO);
    }

    @Test
    void findById_nonExistingId_shouldThrowResourceNotFoundException() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(atendimentoRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> atendimentoService.findById(uuid));
    }

    @Test
    void findAll_multipleAtendimentosExist_shouldReturnListOfAtendimentoDTOs() {
        // Arrange
        Atendimento atendimento1 = new Atendimento();
        atendimento1.setAtendimentoUuid(UUID.randomUUID());
        Atendimento atendimento2 = new Atendimento();
        atendimento2.setAtendimentoUuid(UUID.randomUUID());
        List<Atendimento> atendimentos = Arrays.asList(atendimento1, atendimento2);

        when(atendimentoRepository.findAll()).thenReturn(atendimentos);

        // Act
        List<AtendimentoDTO> actualDTOs = atendimentoService.findAll();

        // Assert
        Assertions.assertEquals(atendimentos.size(), actualDTOs.size());
        Assertions.assertTrue(actualDTOs.stream().anyMatch(dto -> dto.atendimentoUuid().equals(atendimento1.getAtendimentoUuid())));
        Assertions.assertTrue(actualDTOs.stream().anyMatch(dto -> dto.atendimentoUuid().equals(atendimento2.getAtendimentoUuid())));
    }

    @Test
    void findAll_noAtendimentosExist_shouldReturnEmptyList() {
        // Arrange
        when(atendimentoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<AtendimentoDTO> actualDTOs = atendimentoService.findAll();

        // Assert
        Assertions.assertTrue(actualDTOs.isEmpty());
    }

    @Test
    void insert_validAtendimentoDTO_shouldReturnInsertedAtendimentoDTO() {
        // Arrange
        AtendimentoDTO dto = new AtendimentoDTO(
                UUID.randomUUID(),
                new Pets(),
                "Descrição",
                "10.00",
                "2023-05-30"
        );
        Atendimento expectedEntity = new Atendimento(dto);

        when(atendimentoRepository.save(any(Atendimento.class))).thenReturn(expectedEntity);

        // Act
        AtendimentoDTO actualDTO = atendimentoService.insert(dto);

        // Assert
        Assertions.assertEquals(expectedEntity.getAtendimentoUuid(), actualDTO.atendimentoUuid());
        Assertions.assertEquals(expectedEntity.getPet(), actualDTO.pet());
        Assertions.assertEquals(expectedEntity.getDescricaoDoAtendimento(), actualDTO.descricaoDoAtendimento());
        Assertions.assertEquals(expectedEntity.getValorDoAtendimento(), actualDTO.valorDoAtendimento());
        Assertions.assertEquals(expectedEntity.getDataDoAtendimento(), actualDTO.dataDoAtendimento());
    }

    @Test
    void update_existingIdAndValidAtendimentoDTO_shouldReturnUpdatedAtendimentoDTO() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Atendimento existingAtendimento = new Atendimento();
        existingAtendimento.setAtendimentoUuid(uuid);

        AtendimentoDTO dto = new AtendimentoDTO(
                uuid,
                new Pets(),
                "Descrição atualizada",
                "20.00",
                "2023-05-31"
        );
        Atendimento expectedEntity = new Atendimento(dto);

        when(atendimentoRepository.findById(uuid)).thenReturn(Optional.of(existingAtendimento));
        when(atendimentoRepository.save(any(Atendimento.class))).thenReturn(expectedEntity);

        // Act
        AtendimentoDTO actualDTO = atendimentoService.update(uuid, dto);

        // Assert
        Assertions.assertEquals(expectedEntity.getAtendimentoUuid(), actualDTO.atendimentoUuid());
        Assertions.assertEquals(expectedEntity.getPet(), actualDTO.pet());
        Assertions.assertEquals(expectedEntity.getDescricaoDoAtendimento(), actualDTO.descricaoDoAtendimento());
        Assertions.assertEquals(expectedEntity.getValorDoAtendimento(), actualDTO.valorDoAtendimento());
        Assertions.assertEquals(expectedEntity.getDataDoAtendimento(), actualDTO.dataDoAtendimento());
    }

    @Test
    void update_nonExistingId_shouldThrowResourceNotFoundException() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        AtendimentoDTO dto = new AtendimentoDTO(
                uuid,
                new Pets(),
                "Descrição",
                "10.00",
                "2023-05-30"
        );

        when(atendimentoRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> atendimentoService.update(uuid, dto));
    }

    @Test
    void delete_existingId_shouldDeleteAtendimento() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(atendimentoRepository.existsById(uuid)).thenReturn(true);

        // Act
        atendimentoService.delete(uuid);

        // Assert
        verify(atendimentoRepository, times(1)).deleteById(uuid);
    }

    @Test
    void delete_nonExistingId_shouldThrowResourceNotFoundException() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(atendimentoRepository.existsById(uuid)).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(ResourceNotFoundException.class, () -> atendimentoService.delete(uuid));
    }

    @Test
    void delete_existingIdAndDataIntegrityViolation_shouldThrowDatabaseException() {
        // Arrange
        UUID uuid = UUID.randomUUID();

        when(atendimentoRepository.existsById(uuid)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(atendimentoRepository).deleteById(uuid);

        // Act & Assert
        Assertions.assertThrows(DatabaseException.class, () -> atendimentoService.delete(uuid));
    }
}