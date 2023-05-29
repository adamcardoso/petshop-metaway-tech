package com.metaway.petshop.services.impl;

import com.metaway.petshop.dto.PetsDTO;
import com.metaway.petshop.entities.Pets;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.PetsRepository;
import com.metaway.petshop.services.interfaces.PetsService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class PetsServiceImpl implements PetsService {


    private final PetsRepository petsRepository;

    public PetsServiceImpl(PetsRepository petsRepository) {
        this.petsRepository = petsRepository;
    }

    @Override
    public PetsDTO findById(UUID uuid) {
        Optional<Pets> obj = petsRepository.findById(uuid);
        Pets entity = obj.orElseThrow(() -> new ResourceNotFoundException("Error! Entity not found"));

        return new PetsDTO(entity);
    }

    @Override
    public List<PetsDTO> findAll() {
        Iterable<Pets> pets = petsRepository.findAll();
        List<Pets> petsList = new ArrayList<>();

        for (Pets pet : pets) {
            petsList.add(pet);
        }

        return petsList.stream().map(PetsDTO::new).collect(Collectors.toList());
    }

    @Override
    public PetsDTO insert(PetsDTO dto) {
        Pets entity = new Pets();
        copyDtoToEntity(dto, entity);
        entity = petsRepository.save(entity);
        return new PetsDTO(entity);
    }

    @Override
    public PetsDTO update(UUID uuid, PetsDTO petsDTO) {
        try{
            Optional<Pets> optionalPets = petsRepository.findById(uuid);
            if (optionalPets.isPresent()){
                Pets entity = optionalPets.get();
                copyDtoToEntity(petsDTO, entity);
                entity = petsRepository.save(entity);
                return new PetsDTO(entity);
            }else {
                throw new ResourceNotFoundException("Id não encontrado " + uuid);
            }
        }catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("Id not found " + uuid);
        }
    }

    @Override
    public void delete(UUID id) {
        if (!petsRepository.existsById(id)) {
            throw new ResourceNotFoundException("Erro! Atebdunebti com id: " + id + " não encontrado!");
        }
        try {
            petsRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Error! Integrity violation");
        }
    }

    private void copyDtoToEntity(PetsDTO dto, Pets entity) {
        entity.setDataDeNascimentoDoPet(dto.dataDeNascimentoDoPet());
        entity.setNomeDoPet(dto.nomeDoPet());
    }
}
