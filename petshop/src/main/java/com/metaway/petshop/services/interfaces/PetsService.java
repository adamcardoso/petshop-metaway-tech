package com.metaway.petshop.services.interfaces;

import com.metaway.petshop.dto.PetsDTO;

import java.util.List;
import java.util.UUID;

public interface PetsService {

    PetsDTO findById(UUID uuid);

    List<PetsDTO> findAll();

    PetsDTO insert(PetsDTO dto);

    PetsDTO update(UUID uuid, PetsDTO petsDTO);

    void delete(UUID uuid);
}

