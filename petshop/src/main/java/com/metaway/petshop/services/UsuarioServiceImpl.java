package com.metaway.petshop.services;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import com.metaway.petshop.entities.Perfil;
import com.metaway.petshop.entities.Usuario;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.EntityNotFoundException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.PerfilRepository;
import com.metaway.petshop.repositories.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

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
        Optional<Usuario> obj = usuarioRepository.findById(uuid);
        Usuario entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new UsuarioDTO(entity);
    }

    @Override
    public UsuarioDTO insert(UsuarioInsertDTO usuarioInsertDTO) {
        Usuario entity = new Usuario();

        copyDtoToEntity(usuarioInsertDTO.userDTO(), entity);
        entity.setSenha(passwordEncoder.encode(usuarioInsertDTO.getSenha())); // encrypting the senha

        entity = usuarioRepository.save(entity);

        return new UsuarioDTO(entity);
    }

    @Override
    public UsuarioDTO update(UUID id, UsuarioUpdateDTO usuarioUpdateDTO) {
        try {
            Optional<Usuario> optionalEntity = usuarioRepository.findById(id);
            if (optionalEntity.isPresent()) {
                Usuario entity = optionalEntity.get();
                copyDtoToEntity(usuarioUpdateDTO.userDTO(), entity);
                entity = usuarioRepository.save(entity);

                return new UsuarioDTO(entity);
            } else {
                throw new ResourceNotFoundException("Error! Id not found " + id);
            }
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }
    }

    @Override
    public void delete(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Error! Person with id " + id + " not found");
        }
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }


    @Override
    public List<UsuarioDTO> findAll() {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        List<Usuario> listaDeUsuarios = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            listaDeUsuarios.add(usuario);
        }
        return listaDeUsuarios.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    private void copyDtoToEntity(UsuarioDTO userDTO, Usuario entity) {
        entity.setNomeDoUsuario(userDTO.getNomeDoUsuario());
        entity.setCpf(userDTO.getCpf());
        entity.setSenha(passwordEncoder.encode(userDTO.getSenha()));

        entity.getPerfis().clear();

        for (Perfil perfil : userDTO.getPerfis()) {
            perfilRepository.findById(perfil.getPerfilUuid()).ifPresent(entity.getPerfis()::add);
        }
    }

    /*private void copyDtoToEntity(UsuarioUpdateDTO userDTO, Usuario entity) {
        entity.setNomeDoUsuario(userDTO.getUsername());
    }*/
}
