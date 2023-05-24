package com.metaway.petshop.services;

import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.dto.EnderecoDTO;
import com.metaway.petshop.dto.PetsDTO;
import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.repositories.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }



    private Cliente copyDtoToEntity(ClienteDTO dto) {
        Cliente entity = new Cliente();
        entity.setNomeDoCliente(dto.getNomeDoCliente());
        entity.setCpf(dto.getCpf());
        entity.setDataDeCadastro(dto.getDataDeCadastro());
        entity.setEnderecos(dto.getEnderecos().stream()
                .map(EnderecoDTO::toEntity)
                .collect(Collectors.toList()));
        entity.setPets(dto.getPets().stream()
                .map(PetsDTO::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }
}
