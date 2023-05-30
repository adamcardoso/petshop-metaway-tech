package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.PetsDTO;
import com.metaway.petshop.entities.Pets;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.PetsRepository;
import com.metaway.petshop.services.interfaces.PetsService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PetsServiceImpl implements PetsService {

    private final PetsRepository petsRepository;

    public PetsServiceImpl(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    @Override
    public PetsDTO findById(UUID uuid) {
        Pets entity = petsRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Pet not found: " + uuid));
        return PetsDTO.fromEntity(entity);
    }

    @Override
    public List<PetsDTO> findAll() {
        Iterable<Pets> pets = petsRepository.findAll();
        return StreamSupport.stream(pets.spliterator(), false)
                .map(PetsDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PetsDTO insert(PetsDTO dto) {
        Pets entity = dto.toEntity();
        entity = petsRepository.save(entity);
        return PetsDTO.fromEntity(entity);
    }

    @Override
    public PetsDTO update(UUID uuid, PetsDTO petsDTO) {
        Optional<Pets> optionalPets = petsRepository.findById(uuid);
        Pets entity = optionalPets.orElseThrow(() -> new ResourceNotFoundException("Pet not found: " + uuid));
        copyDtoToEntity(petsDTO, entity);
        entity = petsRepository.save(entity);
        return PetsDTO.fromEntity(entity);
    }

    @Override
    public void delete(UUID id) {
        if (!petsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Pet not found: " + id);
        }
        try {
            petsRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error deleting pet: " + id);
        }
    }

    private void copyDtoToEntity(PetsDTO dto, Pets entity) {
        entity.setDataDeNascimentoDoPet(dto.dataDeNascimentoDoPet());
        entity.setNomeDoPet(dto.nomeDoPet());
    }
}
