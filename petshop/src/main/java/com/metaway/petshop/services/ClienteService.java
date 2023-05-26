package com.metaway.petshop.services;

import com.metaway.petshop.dto.ClienteDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClienteService {
    List<ClienteDTO> findAll();

    Optional<ClienteDTO> findById(UUID uuid);

    List<ClienteDTO> findByName(String nomeDoCliente);

    ClienteDTO insert(ClienteDTO dto);

    ClienteDTO update(UUID uuid, ClienteDTO clienteDTO);

    void delete(UUID uuid);
}
