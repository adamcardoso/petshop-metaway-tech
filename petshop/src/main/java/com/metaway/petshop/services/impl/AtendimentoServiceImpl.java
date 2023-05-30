package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.AtendimentoDTO;
import com.metaway.petshop.entities.Atendimento;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.AtendimentoRepository;
import com.metaway.petshop.services.interfaces.AtendimentoService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AtendimentoServiceImpl implements AtendimentoService {

    private final AtendimentoRepository atendimentoRepository;

    public AtendimentoServiceImpl(AtendimentoRepository atendimentoRepository) {
        this.atendimentoRepository = atendimentoRepository;
    }

    @Override
    public AtendimentoDTO findById(UUID uuid) {
        Atendimento entity = atendimentoRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Atendimento not found: " + uuid));
        return new AtendimentoDTO(entity);
    }

    @Override
    public List<AtendimentoDTO> findAll() {
        Iterable<Atendimento> atendimentos = atendimentoRepository.findAll();
        return StreamSupport.stream(atendimentos.spliterator(), false)
                .map(AtendimentoDTO::new)
                .collect(Collectors.toList());
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
        Optional<Atendimento> optionalAtendimento = atendimentoRepository.findById(uuid);
        Atendimento entity = optionalAtendimento.orElseThrow(() -> new ResourceNotFoundException("Atendimento not found: " + uuid));
        copyDtoToEntity(atendimentoDTO, entity);
        entity = atendimentoRepository.save(entity);
        return new AtendimentoDTO(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!atendimentoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Atendimento not found: " + id);
        }
        try {
            atendimentoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error deleting atendimento: " + id);
        }
    }

    private void copyDtoToEntity(AtendimentoDTO dto, Atendimento entity) {
        entity.setPet(dto.pet());
        entity.setDescricaoDoAtendimento(dto.descricaoDoAtendimento());
        entity.setValorDoAtendimento(dto.valorDoAtendimento());
        entity.setDataDoAtendimento(dto.dataDoAtendimento());
    }
}
