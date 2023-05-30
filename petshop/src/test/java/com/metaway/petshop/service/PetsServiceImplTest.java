package com.metaway.petshop.service;

import com.metaway.petshop.dto.PetsDTO;
import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.entities.Pets;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.PetsRepository;
import com.metaway.petshop.services.impl.PetsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class PetsServiceImplTest {

    @Mock
    private PetsRepository petsRepository;

    @InjectMocks
    private PetsServiceImpl petsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_nonExistingId_throwsResourceNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(petsRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> petsService.findById(uuid));
    }

    @Test
    void update_nonExistingId_throwsResourceNotFoundException() {
        UUID uuid = UUID.randomUUID();
        PetsDTO petsDTO = new PetsDTO(uuid, UUID.randomUUID(), UUID.randomUUID(), null, "Updated Pet");

        when(petsRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> petsService.update(uuid, petsDTO));
    }

    @Test
    void delete_existingId_deletesPets() {
        UUID uuid = UUID.randomUUID();

        when(petsRepository.existsById(uuid)).thenReturn(true);

        petsService.delete(uuid);

        verify(petsRepository, times(1)).deleteById(uuid);
    }

    @Test
    void delete_nonExistingId_throwsResourceNotFoundException() {
        UUID uuid = UUID.randomUUID();

        when(petsRepository.existsById(uuid)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> petsService.delete(uuid));
    }

    @Test
    void delete_existingId_dataIntegrityViolationException_throwsDatabaseException() {
        UUID uuid = UUID.randomUUID();

        when(petsRepository.existsById(uuid)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(petsRepository).deleteById(uuid);

        assertThrows(DatabaseException.class, () -> petsService.delete(uuid));
    }
}
