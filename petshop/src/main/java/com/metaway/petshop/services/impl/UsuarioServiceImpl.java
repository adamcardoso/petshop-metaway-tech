package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import com.metaway.petshop.entities.Perfil;
import com.metaway.petshop.entities.Usuario;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.PerfilRepository;
import com.metaway.petshop.repositories.UsuarioRepository;
import com.metaway.petshop.services.interfaces.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public UsuarioServiceImpl(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    public UsuarioDTO findById(UUID uuid) {
        Usuario entity = usuarioRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + uuid));
        return new UsuarioDTO(entity);
    }

    @Override
    public UsuarioDTO insert(UsuarioInsertDTO usuarioInsertDTO) {
        Usuario entity = new Usuario();
        copyDtoToEntity(usuarioInsertDTO.userDTO(), entity);
        entity.setSenha(passwordEncoder.encode(usuarioInsertDTO.getSenha()));
        entity = usuarioRepository.save(entity);
        return new UsuarioDTO(entity);
    }

    @Override
    public UsuarioDTO update(UUID id, UsuarioUpdateDTO usuarioUpdateDTO) {
        Usuario entity = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        copyDtoToEntity(usuarioUpdateDTO.userDTO(), entity);
        entity = usuarioRepository.save(entity);
        return new UsuarioDTO(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error deleting user: " + id);
        }
    }

    @Override
    public List<UsuarioDTO> findAll() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        return StreamSupport.stream(usuarios.spliterator(), false)
                .map(UsuarioDTO::new)
                .collect(Collectors.toList());
    }

    private void copyDtoToEntity(UsuarioDTO userDTO, Usuario entity) {
        entity.setNomeDoUsuario(userDTO.nomeDoUsuario());
        entity.setCpf(userDTO.cpf());
        entity.setSenha(passwordEncoder.encode(userDTO.senha()));
        entity.getPerfis().clear();
        for (Perfil perfil : userDTO.perfis()) {
            perfilRepository.findById(perfil.getPerfilUuid())
                    .ifPresent(entity.getPerfis()::add);
        }
    }
}
