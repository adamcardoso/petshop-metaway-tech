package com.metaway.petshop.services;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {

    @Transactional(readOnly = true)
    UsuarioDTO findById(UUID uuid);

    @Transactional
    UsuarioDTO insert(UsuarioInsertDTO usuarioInsertDTO);

    @Transactional
    UsuarioDTO update(UUID id, UsuarioUpdateDTO usuarioUpdateDTO);

    void delete(UUID id);

    @Transactional(readOnly = true)
    List<UsuarioDTO> findAll();
}