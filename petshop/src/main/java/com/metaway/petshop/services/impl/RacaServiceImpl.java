package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.RacaDTO;
import com.metaway.petshop.entities.Raca;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.RacaRepository;
import com.metaway.petshop.services.interfaces.RacaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class RacaServiceImpl implements RacaService {

    private final RacaRepository racaRepository;

    public RacaServiceImpl(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    @Override
    public RacaDTO findById(UUID uuid) {
        Raca entity = racaRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Raca not found: " + uuid));
        return new RacaDTO(entity);
    }

    @Override
    public List<RacaDTO> findAll() {
        Iterable<Raca> racas = racaRepository.findAll();
        return StreamSupport.stream(racas.spliterator(), false)
                .map(RacaDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public RacaDTO insert(RacaDTO dto) {
        Raca entity = dto.toEntity();
        entity = racaRepository.save(entity);
        return new RacaDTO(entity);
    }

    @Override
    public RacaDTO update(UUID uuid, RacaDTO racaDTO) {
        Optional<Raca> optionalRaca = racaRepository.findById(uuid);
        Raca entity = optionalRaca.orElseThrow(() -> new ResourceNotFoundException("Raca not found: " + uuid));
        copyDtoToEntity(racaDTO, entity);
        entity = racaRepository.save(entity);
        return new RacaDTO(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!racaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Raca not found: " + id);
        }
        try {
            racaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error deleting raca: " + id);
        }
    }

    private void copyDtoToEntity(RacaDTO dto, Raca entity) {
        entity.setRacaUuid(dto.racaUuid());
        entity.setDescricao(dto.descricao());
    }
}
