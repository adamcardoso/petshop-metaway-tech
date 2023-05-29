package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.RacaDTO;
import com.metaway.petshop.entities.Raca;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.RacaRepository;
import com.metaway.petshop.services.interfaces.RacaService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class RacaServiceImpl implements RacaService {


    private final RacaRepository racaRepository;

    public RacaServiceImpl(RacaRepository racaRepository) {
        this.racaRepository = racaRepository;
    }

    @Override
    public RacaDTO findById(UUID uuid) {
        Optional<Raca> obj = racaRepository.findById(uuid);
        Raca entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new RacaDTO(entity);
    }

    @Override
    public List<RacaDTO> findAll() {
        Iterable<Raca> racas = racaRepository.findAll();
        List<Raca> racaList = new ArrayList<>();

        for (Raca raca : racas) {
            racaList.add(raca);
        }

        return racaList.stream().map(RacaDTO::new).collect(Collectors.toList());
    }

    @Override
    public RacaDTO insert(RacaDTO dto) {
        Raca entity = new Raca();
        copyDtoToEntity(dto, entity);
        entity = racaRepository.save(entity);
        return new RacaDTO(entity);
    }

    @Override
    public RacaDTO update(UUID uuid, RacaDTO racaDTO) {
        try{
            Optional<Raca> optionalRacas = racaRepository.findById(uuid);
            if (optionalRacas.isPresent()){
                Raca entity = optionalRacas.get();
                copyDtoToEntity(racaDTO, entity);
                entity = racaRepository.save(entity);
                return new RacaDTO(entity);
            }else {
                throw new ResourceNotFoundException("Id não encontrado " + uuid);
            }
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
    }

    @Override
    public void delete(UUID id) {
        if (!racaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Erro! Atebdunebti com id: " + id + " não encontrado!");
        }
        try {
            racaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }

    private void copyDtoToEntity(RacaDTO dto, Raca entity) {
        entity.setRacaUuid(dto.racaUuid());
        entity.setDescricao(dto.descricao());
    }
}
