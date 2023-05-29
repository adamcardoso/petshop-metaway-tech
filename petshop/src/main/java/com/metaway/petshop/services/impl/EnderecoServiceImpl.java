package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.EnderecoDTO;
import com.metaway.petshop.entities.Endereco;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.EnderecoRepository;
import com.metaway.petshop.services.interfaces.EnderecoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


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

        return new EnderecoDTO(entity);
    }

    @Override
    public List<EnderecoDTO> findAll() {
        Iterable<Endereco> atendimentos = enderecoRepository.findAll();
        List<Endereco> atendimentoList = new ArrayList<>();

        for (Endereco atendimento : atendimentos) {
            atendimentoList.add(atendimento);
        }

        return atendimentoList.stream().map(EnderecoDTO::new).collect(Collectors.toList());
    }

    @Override
    public EnderecoDTO insert(EnderecoDTO dto) {
        Endereco entity = new Endereco();
        copyDtoToEntity(dto, entity);
        entity = enderecoRepository.save(entity);
        return new EnderecoDTO(entity);
    }

    @Override
    public EnderecoDTO update(UUID uuid, EnderecoDTO enderecoDTO) {
        try{
            Optional<Endereco> optionalAtendimento = enderecoRepository.findById(uuid);
            if (optionalAtendimento.isPresent()){
                Endereco entity = optionalAtendimento.get();
                copyDtoToEntity(enderecoDTO, entity);
                entity = enderecoRepository.save(entity);
                return new EnderecoDTO(entity);
            }else {
                throw new ResourceNotFoundException("Id não encontrado " + uuid);
            }
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
    }

    @Override
    public void delete(UUID id) {
        if (!enderecoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Erro! Atebdunebti com id: " + id + " não encontrado!");
        }
        try {
            enderecoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }

    private void copyDtoToEntity(EnderecoDTO dto, Endereco entity) {
        entity.setEnderecoUuid(dto.enderecoUuid());
        entity.setLogradouro(dto.logradouro());
        entity.setCidade(dto.cidade());
        entity.setBairro(dto.bairro());
        entity.setComplemento(dto.complemento());
        entity.setTag(dto.tag());
    }
}
