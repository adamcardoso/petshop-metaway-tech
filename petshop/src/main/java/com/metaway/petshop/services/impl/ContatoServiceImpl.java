package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.ContatoDTO;
import com.metaway.petshop.entities.Contato;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.ContatoRepository;
import com.metaway.petshop.services.interfaces.ContatoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class ContatoServiceImpl implements ContatoService {


    private final ContatoRepository contatoRepository;

    public ContatoServiceImpl(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    @Override
    public ContatoDTO findById(UUID uuid) {
        Optional<Contato> obj = contatoRepository.findById(uuid);
        Contato entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new ContatoDTO(entity);
    }

    @Override
    public List<ContatoDTO> findAll() {
        Iterable<Contato> atendimentos = contatoRepository.findAll();
        List<Contato> atendimentoList = new ArrayList<>();

        for (Contato atendimento : atendimentos) {
            atendimentoList.add(atendimento);
        }

        return atendimentoList.stream().map(ContatoDTO::new).collect(Collectors.toList());
    }

    @Override
    public ContatoDTO insert(ContatoDTO dto) {
        Contato entity = new Contato();
        copyDtoToEntity(dto, entity);
        entity = contatoRepository.save(entity);
        return new ContatoDTO(entity);
    }

    @Override
    public ContatoDTO update(UUID uuid, ContatoDTO contatoDTO) {
        try{
            Optional<Contato> optionalAtendimento = contatoRepository.findById(uuid);
            if (optionalAtendimento.isPresent()){
                Contato entity = optionalAtendimento.get();
                copyDtoToEntity(contatoDTO, entity);
                entity = contatoRepository.save(entity);
                return new ContatoDTO(entity);
            }else {
                throw new ResourceNotFoundException("Id não encontrado " + uuid);
            }
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
    }

    @Override
    public void delete(UUID id) {
        if (!contatoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Erro! Atebdunebti com id: " + id + " não encontrado!");
        }
        try {
            contatoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }

    private void copyDtoToEntity(ContatoDTO dto, Contato entity) {
        entity.setTag(dto.tag());
        entity.setEmail(dto.email());
        entity.setTelefone(dto.telefone());
        entity.setValor(dto.valor());
        entity.setContatoUuid(dto.contatoUuid());
    }
}
