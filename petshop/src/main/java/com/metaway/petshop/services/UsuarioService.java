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
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final PasswordEncoder passwordEncoder;
    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    public UsuarioService(PasswordEncoder passwordEncoder, UsuarioRepository usuarioRepository, PerfilRepository perfilRepository) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(UUID uuid) {
        Optional<Usuario> obj = usuarioRepository.findById(uuid);
        Usuario entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new UsuarioDTO(entity);
    }

    @Transactional
    public UsuarioDTO insert(UsuarioInsertDTO userInsertDTO) {
        Usuario entity = new Usuario();

        copyDtoToEntity(userInsertDTO, entity);
        entity.setSenha(passwordEncoder.encode(userInsertDTO.senha())); // encrypting the senha

        entity = usuarioRepository.save(entity);

        return new UsuarioDTO(entity);
    }

    @Transactional
    public UsuarioDTO update(UUID id, UsuarioUpdateDTO userDTO) {
        try {
            Optional<Usuario> optionalEntity = usuarioRepository.findById(id);
            if (optionalEntity.isPresent()) {
                Usuario entity = optionalEntity.get();
                copyDtoToEntity(userDTO, entity);
                entity = usuarioRepository.save(entity);

                return new UsuarioDTO(entity);
            } else {
                throw new ResourceNotFoundException("Error! Id not found " + id);
            }
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Error! Id not found " + id);
        }
    }

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


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        Iterable<Usuario> persons = usuarioRepository.findAll();
        List<Usuario> personModelList = new ArrayList<>();
        for (Usuario userModel : persons) {
            personModelList.add(userModel);
        }
        return personModelList.stream().map(UsuarioDTO::new).collect(Collectors.toList());
    }

    private void copyDtoToEntity(UsuarioInsertDTO userDTO, Usuario entity) {
        entity.setNomeDoUsuario(userDTO.nomeDoUsuario());
        entity.setCpf(userDTO.cpf());
        entity.setSenha(passwordEncoder.encode(userDTO.senha()));

        entity.getPerfis().clear();

        for (Perfil perfil : userDTO.perfis()) {
            perfilRepository.findById(perfil.getPerfilUuid()).ifPresent(entity.getPerfis()::add);
        }
    }

    private void copyDtoToEntity(UsuarioUpdateDTO userDTO, Usuario entity) {
        entity.setNomeDoUsuario(userDTO.nomeDoUsuario());
    }
}
