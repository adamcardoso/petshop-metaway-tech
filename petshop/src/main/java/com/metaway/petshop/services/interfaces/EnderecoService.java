package com.metaway.petshop.services.interfaces;

import com.metaway.petshop.dto.EnderecoDTO;

import java.util.List;
import java.util.UUID;

public interface EnderecoService {

    EnderecoDTO findById(UUID uuid);

    List<EnderecoDTO> findAll();

    EnderecoDTO insert(EnderecoDTO dto);

    EnderecoDTO update(UUID uuid, EnderecoDTO enderecoDTO);

    void delete(UUID uuid);
}

