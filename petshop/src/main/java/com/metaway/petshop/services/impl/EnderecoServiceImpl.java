package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.EnderecoDTO;
import com.metaway.petshop.entities.Endereco;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.EnderecoRepository;
import com.metaway.petshop.services.interfaces.EnderecoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Service
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository enderecoRepository;

    public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    @Override
    public EnderecoDTO findById(UUID uuid) {
        Optional<Endereco> obj = enderecoRepository.findById(uuid);
        Endereco entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return EnderecoDTO.fromEntity(entity);
    }

    @Override
    public List<EnderecoDTO> findAll() {
        Iterable<Endereco> enderecos = enderecoRepository.findAll();

        return StreamSupport.stream(enderecos.spliterator(), false)
                .map(EnderecoDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public EnderecoDTO insert(EnderecoDTO dto) {
        Endereco entity = dto.toEntity();
        entity = enderecoRepository.save(entity);
        return EnderecoDTO.fromEntity(entity);
    }

    @Override
    public EnderecoDTO update(UUID uuid, EnderecoDTO enderecoDTO) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(uuid);
        Endereco entity = optionalEndereco.orElseThrow(() -> new ResourceNotFoundException("Id not found " + uuid));

        copyDtoToEntity(enderecoDTO, entity);
        entity = enderecoRepository.save(entity);
        return EnderecoDTO.fromEntity(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Endereço com ID " + id + " não encontrado!");
        }

        try {
            enderecoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Integridade do banco de dados violada");
        }
    }

    private void copyDtoToEntity(EnderecoDTO dto, Endereco entity) {
        entity.setLogradouro(dto.logradouro());
        entity.setCidade(dto.cidade());
        entity.setBairro(dto.bairro());
        entity.setComplemento(dto.complemento());
        entity.setTag(dto.tag());
    }
}
