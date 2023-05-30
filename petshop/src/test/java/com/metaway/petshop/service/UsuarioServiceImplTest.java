package com.metaway.petshop.service;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import com.metaway.petshop.entities.Perfil;
import com.metaway.petshop.entities.Usuario;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.PerfilRepository;
import com.metaway.petshop.repositories.UsuarioRepository;
import com.metaway.petshop.services.impl.UsuarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioServiceImplTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PerfilRepository perfilRepository;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ExistingId_ReturnsUsuarioDTO() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        Usuario usuario = new Usuario();
        usuario.setUsuarioUuid(uuid);
        when(usuarioRepository.findById(uuid)).thenReturn(Optional.of(usuario));

        // Act
        UsuarioDTO result = usuarioService.findById(uuid);

        // Assert
        assertNotNull(result);
        assertEquals(uuid, result.usuarioUuid());
    }

    @Test
    void findById_NonExistingId_ThrowsResourceNotFoundException() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(usuarioRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.findById(uuid));
    }

    @Test
    void update_NonExistingId_ThrowsResourceNotFoundException() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        UsuarioUpdateDTO usuarioUpdateDTO = createValidUsuarioUpdateDTO();
        when(usuarioRepository.findById(uuid)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.update(uuid, usuarioUpdateDTO));
    }

    @Test
    void delete_ExistingId_DeletesUsuario() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(usuarioRepository.existsById(uuid)).thenReturn(true);

        // Act
        assertDoesNotThrow(() -> usuarioService.delete(uuid));

        // Assert
        verify(usuarioRepository, times(1)).deleteById(uuid);
    }

    @Test
    void delete_NonExistingId_ThrowsResourceNotFoundException() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(usuarioRepository.existsById(uuid)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> usuarioService.delete(uuid));
    }

    @Test
    void delete_DatabaseExceptionOccurs_ThrowsDatabaseException() {
        // Arrange
        UUID uuid = UUID.randomUUID();
        when(usuarioRepository.existsById(uuid)).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(usuarioRepository).deleteById(uuid);

        // Act & Assert
        assertThrows(DatabaseException.class, () -> usuarioService.delete(uuid));
    }


    private UsuarioUpdateDTO createValidUsuarioUpdateDTO() {
        Set<Perfil> perfis = new HashSet<>();
        perfis.add(new Perfil(UUID.randomUUID(), "CLIENTE"));
        return new UsuarioUpdateDTO("newUsername", new UsuarioDTO());
    }
}
