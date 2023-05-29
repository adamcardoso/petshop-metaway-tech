package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.AtendimentoDTO;
import com.metaway.petshop.entities.Atendimento;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.AtendimentoRepository;
import com.metaway.petshop.services.interfaces.AtendimentoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class AtendimentoServiceImpl implements AtendimentoService {


    private final AtendimentoRepository atendimentoRepository;

    public AtendimentoServiceImpl(AtendimentoRepository atendimentoRepository) {
        this.atendimentoRepository = atendimentoRepository;
    }

    @Override
    public AtendimentoDTO findById(UUID uuid) {
        Optional<Atendimento> obj = atendimentoRepository.findById(uuid);
        Atendimento entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new AtendimentoDTO(entity);
    }

    @Override
    public List<AtendimentoDTO> findAll() {
        Iterable<Atendimento> atendimentos = atendimentoRepository.findAll();
        List<Atendimento> atendimentoList = new ArrayList<>();

        for (Atendimento atendimento : atendimentos) {
            atendimentoList.add(atendimento);
        }

        return atendimentoList.stream().map(AtendimentoDTO::new).collect(Collectors.toList());
    }

    @Override
    public AtendimentoDTO insert(AtendimentoDTO dto) {
        Atendimento entity = new Atendimento();
        copyDtoToEntity(dto, entity);
        entity = atendimentoRepository.save(entity);
        return new AtendimentoDTO(entity);
    }

    @Override
    public AtendimentoDTO update(UUID uuid, AtendimentoDTO atendimentoDTO) {
        try{
            Optional<Atendimento> optionalAtendimento = atendimentoRepository.findById(uuid);
            if (optionalAtendimento.isPresent()){
                Atendimento entity = optionalAtendimento.get();
                copyDtoToEntity(atendimentoDTO, entity);
                entity = atendimentoRepository.save(entity);
                return new AtendimentoDTO(entity);
            }else {
                throw new ResourceNotFoundException("Id não encontrado " + uuid);
            }
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
    }

    @Override
    public void delete(UUID id) {
        if (!atendimentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Erro! Atebdunebti com id: " + id + " não encontrado!");
        }
        try {
            atendimentoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }

    private void copyDtoToEntity(AtendimentoDTO dto, Atendimento entity) {
        entity.setPet(dto.pet());
        entity.setDescricaoDoAtendimento(dto.descricaoDoAtendimento());
        entity.setValorDoAtendimento(dto.valorDoAtendimento());
        entity.setDataDoAtendimento(dto.dataDoAtendimento());
    }
}
