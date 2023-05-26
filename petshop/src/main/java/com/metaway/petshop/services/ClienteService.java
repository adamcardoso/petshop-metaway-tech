package com.metaway.petshop.services;

import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.dto.EnderecoDTO;
import com.metaway.petshop.dto.PetsDTO;
import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.ClienteRepository;
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
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Transactional(readOnly = true)
    public List<ClienteDTO> findAll() {
        Iterable<Cliente> clientes = clienteRepository.findAll();
        List<Cliente> clienteList = new ArrayList<>();

        for (Cliente cliente : clientes) {
            clienteList.add(cliente);
        }

        return clienteList.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    @Transactional
    public Optional<ClienteDTO> findById(UUID uuid) {
        Optional<Cliente> obj = clienteRepository.findById(uuid);
        return obj.map(ClienteDTO::new);
    }

    @Transactional
    public List<ClienteDTO> findByName(String nomeDoCliente){
        List<Cliente> list = clienteRepository.findByNomeDoClienteContainingIgnoreCase(nomeDoCliente);

        if (list.isEmpty()) {
            throw new ResourceNotFoundException("Nome não encontrado: " + nomeDoCliente);
        }

        return list.stream().map(ClienteDTO::new).collect(Collectors.toList());
    }

    public ClienteDTO insert(ClienteDTO dto) {
        Cliente entity = new Cliente();
        copyDtoToEntity(dto, entity);
        entity = clienteRepository.save(entity);
        return new ClienteDTO(entity);
    }

    public ClienteDTO update(UUID uuid, ClienteDTO clienteDTO){
        try{
            Optional<Cliente> optionalCliente = clienteRepository.findById(uuid);
            if (optionalCliente.isPresent()){
                Cliente entity = optionalCliente.get();
                copyDtoToEntity(clienteDTO, entity);
                entity = clienteRepository.save(entity);
                return new ClienteDTO(entity);
            }else {
                throw new ResourceNotFoundException("Id não encontrado " + uuid);
            }
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
    }

    public void delete(UUID uuid){
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

    private Cliente copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setNomeDoCliente(dto.nomeDoCliente());
        entity.setCpf(dto.cpf());
        entity.setDataDeCadastro(dto.dataDeCadastro());
        entity.setEnderecos(dto.enderecos().stream()
                .map(EnderecoDTO::toEntity)
                .collect(Collectors.toList()));
        entity.setPets(dto.pets().stream()
                .map(PetsDTO::toEntity)
                .collect(Collectors.toList()));
        return entity;
    }
}
