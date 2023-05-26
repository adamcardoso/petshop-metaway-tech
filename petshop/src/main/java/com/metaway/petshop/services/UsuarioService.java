package com.metaway.petshop.services;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;

import java.util.List;
import java.util.UUID;

public interface UsuarioService {
    UsuarioDTO findById(UUID uuid);

    UsuarioDTO insert(UsuarioInsertDTO userInsertDTO);

    UsuarioDTO update(UUID id, UsuarioUpdateDTO userDTO);

    void delete(UUID id);

    List<UsuarioDTO> findAll();
}