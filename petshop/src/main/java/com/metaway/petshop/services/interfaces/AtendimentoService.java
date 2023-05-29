package com.metaway.petshop.services.interfaces;

import com.metaway.petshop.dto.AtendimentoDTO;

import java.util.List;
import java.util.UUID;

public interface AtendimentoService {

    AtendimentoDTO findById(UUID uuid);

    List<AtendimentoDTO> findAll();

    AtendimentoDTO insert(AtendimentoDTO dto);

    AtendimentoDTO update(UUID uuid, AtendimentoDTO clienteDTO);

    void delete(UUID uuid);
}

