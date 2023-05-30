package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.RacaController;
import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.dto.RacaDTO;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.impl.RacaServiceImpl;
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
public class RacaControllerImpl implements RacaController {

    private static final Logger logger = LoggerFactory.getLogger(RacaControllerImpl.class);

    private final RacaServiceImpl racaService;

    public RacaControllerImpl(RacaServiceImpl racaService) {
        this.racaService = racaService;
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<List<RacaDTO>> findAll() {
        logger.info("Endpoint findAll chamado");

        List<RacaDTO> list = racaService.findAll();

        return ResponseEntity.ok(list);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<RacaDTO> findById(UUID id) {
        logger.info("Endpoint findById chamado para o UUID: {}", id);

        RacaDTO racaDTO = racaService.findById(id);

        return ResponseEntity.ok(racaDTO);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<RacaDTO> insert(@Valid @RequestBody RacaDTO racaDTO) {
        logger.info("Endpoint insert chamado");

        RacaDTO insertedRaca = racaService.insert(racaDTO);

        URI uri = URI.create("/api/raca/" + insertedRaca.racaUuid());
        return ResponseEntity.created(uri).body(insertedRaca);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<RacaDTO> update(UUID id, @Valid @RequestBody RacaDTO racaDTO) {
        logger.info("Endpoint update chamado para o UUID: {}", id);

        try {
            RacaDTO raca = racaService.update(id, racaDTO);
            return ResponseEntity.ok(raca);
        } catch (ResourceNotFoundException e) {
            logger.error("Raça não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<Void> delete(UUID uuid) {
        logger.info("Endpoint delete chamado para o UUID: {}", uuid);

        try {
            racaService.delete(uuid);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Raça não encontrado: {}", uuid);
            return ResponseEntity.notFound().build();
        }
    }
}
