package com.metaway.petshop.services.interfaces;

import com.metaway.petshop.dto.RacaDTO;

import java.util.List;
import java.util.UUID;

public interface RacaService {

    RacaDTO findById(UUID uuid);

    List<RacaDTO> findAll();

    RacaDTO insert(RacaDTO dto);

    RacaDTO update(UUID uuid, RacaDTO racaDTO);

    void delete(UUID uuid);
}

