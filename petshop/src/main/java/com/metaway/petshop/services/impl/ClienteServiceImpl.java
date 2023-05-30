package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.dto.EnderecoDTO;
import com.metaway.petshop.dto.PetsDTO;
import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.ClienteRepository;
import com.metaway.petshop.services.interfaces.ClienteService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    @Transactional
    public Optional<ClienteDTO> findById(UUID clienteUuid) {
        Optional<Cliente> clienteOptional = clienteRepository.findById(clienteUuid);
        return clienteOptional.map(ClienteDTO::new);
    }

    @Override
    @Transactional
    public List<ClienteDTO> findAll() {
        Iterable<Cliente> clientes = clienteRepository.findAll();
        List<Cliente> clienteList = new ArrayList<>();

        for (Cliente cliente : clientes) {
            clienteList.add(cliente);
        }

        return clienteList.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }


    @Override
    @Transactional
    public List<ClienteDTO> findByName(String nomeDoCliente) {
        List<Cliente> list = clienteRepository.findByNomeDoClienteContainingIgnoreCase(nomeDoCliente);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Nome não encontrado: " + nomeDoCliente);
        }

        return list.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ClienteDTO insert(ClienteDTO clienteDTO) {
        Cliente cliente = ClienteDTO.toEntity(clienteDTO);

        // Check if the cliente already exists in the database
        Optional<Cliente> existingClienteOptional = clienteRepository.findByCpf(cliente.getCpf());
        if (existingClienteOptional.isPresent()) {
            // Cliente already exists, update its properties
            Cliente existingCliente = existingClienteOptional.get();
            existingCliente.setNomeDoCliente(cliente.getNomeDoCliente());
            existingCliente.setDataDeCadastro(cliente.getDataDeCadastro());
            existingCliente.setEnderecos(cliente.getEnderecos());
            existingCliente.setPets(cliente.getPets());
            cliente = existingCliente;
        }

        cliente = clienteRepository.save(cliente);
        return new ClienteDTO(cliente);
    }



    @Override
    @Transactional
    public ClienteDTO update(UUID uuid, ClienteDTO clienteDTO) {
        try {
            Optional<Cliente> optionalCliente = clienteRepository.findById(uuid);
            if (optionalCliente.isPresent()) {
                Cliente entity = optionalCliente.get();
                copyDtoToEntity(clienteDTO, entity);
                entity = clienteRepository.save(entity);
                return new ClienteDTO(entity);
            } else {
                throw new ResourceNotFoundException("Id não encontrado " + uuid);
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
    }


    @Override
    public void delete(UUID uuid) {
        try{
            clienteRepository.deleteById(uuid);
            System.out.println("Cliente deletado com sucesso!");
        }catch (EmptyResultDataAccessException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integrity violation");
        }
    }

    private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNomeDoCliente(dto.nomeDoCliente());
        entity.setCpf(dto.cpf());
        entity.setDataDeCadastro(dto.dataDeCadastro());
        entity.setEnderecos(dto.enderecos().stream()
                .map(EnderecoDTO::toEntity)
                .collect(Collectors.toList()));
        entity.setPets(dto.pets().stream()
                .map(PetsDTO::toEntity)
                .collect(Collectors.toList()));
    }
}
