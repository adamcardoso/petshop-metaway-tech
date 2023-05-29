package com.metaway.petshop.services.interfaces;

import com.metaway.petshop.dto.ContatoDTO;

import java.util.List;
import java.util.UUID;

public interface ContatoService {

    ContatoDTO findById(UUID uuid);

    List<ContatoDTO> findAll();

    ContatoDTO insert(ContatoDTO dto);

    ContatoDTO update(UUID uuid, ContatoDTO clienteDTO);

    void delete(UUID uuid);
}

