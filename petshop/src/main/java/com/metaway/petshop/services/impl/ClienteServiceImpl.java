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

import java.util.*;
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
        return clienteRepository.findById(clienteUuid)
                .map(ClienteDTO::new);
    }

    @Override
    @Transactional
    public List<ClienteDTO> findAll() {
        try {
            List<Cliente> clienteList = clienteRepository.findAll();
            return clienteList.stream()
                    .map(ClienteDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Tratar exceção e retornar fallback (lista vazia)
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public List<ClienteDTO> findByName(String nomeDoCliente) {
        try {
            List<Cliente> list = clienteRepository.findByNomeDoClienteContainingIgnoreCase(nomeDoCliente);

            if (list.isEmpty()) {
                // Lançar exceção personalizada
                throw new ResourceNotFoundException("Nome não encontrado: " + nomeDoCliente);
            }

            return list.stream()
                    .map(ClienteDTO::new)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            // Tratar exceção e retornar fallback (lista vazia)
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public ClienteDTO insert(ClienteDTO clienteDTO) {
        try {
            Cliente cliente = ClienteDTO.toEntity(clienteDTO);

            Optional<Cliente> existingClienteOptional = clienteRepository.findByCpf(cliente.getCpf());
            if (existingClienteOptional.isPresent()) {
                Cliente existingCliente = existingClienteOptional.get();
                existingCliente.setNomeDoCliente(cliente.getNomeDoCliente());
                existingCliente.setDataDeCadastro(cliente.getDataDeCadastro());
                existingCliente.setEnderecos(cliente.getEnderecos());
                existingCliente.setPets(cliente.getPets());
                cliente = existingCliente;
            }

            cliente = clienteRepository.save(cliente);
            return new ClienteDTO(cliente);
        } catch (Exception e) {
            // Tratar exceção e retornar fallback (null)
            return null;
        }
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
                throw new ResourceNotFoundException("Id não encontrado: " + uuid);
            }
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found: " + uuid);
        } catch (Exception e) {
            // Tratar exceção e lançar exceção personalizada
            throw new DatabaseException("Erro ao atualizar cliente: " + uuid);
        }
    }

    @Override
    @Transactional
    public void delete(UUID uuid) {
        try {
            clienteRepository.deleteById(uuid);
        } catch (EmptyResultDataAccessException e) {
            // Lançar exceção personalizada
            throw new ResourceNotFoundException("Id not found: " + uuid);
        } catch (DataIntegrityViolationException e) {
            // Lançar exceção personalizada
            throw new DatabaseException("Integrity violation: " + uuid);
        } catch (Exception e) {
            // Tratar exceção e lançar exceção personalizada
            throw new DatabaseException("Erro ao excluir cliente: " + uuid);
        }
    }

    private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNomeDoCliente(dto.nomeDoCliente());
        entity.setDataDeCadastro(dto.dataDeCadastro());

        List<EnderecoDTO> enderecoDTOs = dto.enderecos();

        if (Objects.nonNull(enderecoDTOs)) {
            entity.setEnderecos(enderecoDTOs.stream()
                    .map(EnderecoDTO::toEntity)
                    .collect(Collectors.toList()));
        }
        List<PetsDTO> petsDTOs = dto.pets();
        if (Objects.nonNull(petsDTOs)) {
            entity.setPets(petsDTOs.stream()
                    .map(PetsDTO::toEntity)
                    .collect(Collectors.toList()));
        }
    }
}
