package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.PetsController;
import com.metaway.petshop.dto.PetsDTO;
import com.metaway.petshop.services.impl.PetsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class PetsControllerImpl implements PetsController {

    private static final Logger logger = LoggerFactory.getLogger(PetsControllerImpl.class);

    private final PetsServiceImpl petsService;

    public PetsControllerImpl(PetsServiceImpl petsService) {
        this.petsService = petsService;
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<List<PetsDTO>> findAll() {
        logger.info("Endpoint findAll chamado");

        List<PetsDTO> list = petsService.findAll();

        return ResponseEntity.ok(list);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<PetsDTO> findById(UUID id) {
        logger.info("Endpoint findById chamado para o UUID: {}", id);

        PetsDTO petsDTO = petsService.findById(id);

        return ResponseEntity.ok(petsDTO);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<PetsDTO> insert(@Valid @RequestBody PetsDTO petsDTO) {
        logger.info("Endpoint insert chamado");

        PetsDTO insertedPets = petsService.insert(petsDTO);

        URI uri = URI.create("/api/pets/" + insertedPets.petsUuid());
        return ResponseEntity.created(uri).body(insertedPets);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<PetsDTO> update(UUID id, @Valid @RequestBody PetsDTO petsDTO) {
        logger.info("Endpoint update chamado para o UUID: {}", id);

        PetsDTO pets = petsService.update(id, petsDTO);

        return ResponseEntity.ok(pets);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<Void> delete(UUID id) {
        logger.info("Endpoint delete chamado para o UUID: {}", id);

        petsService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
