package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.EnderecoController;
import com.metaway.petshop.dto.EnderecoDTO;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.impl.EnderecoServiceImpl;
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
public class EnderecoControllerImpl implements EnderecoController {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoControllerImpl.class);

    private final EnderecoServiceImpl enderecoService;

    public EnderecoControllerImpl(EnderecoServiceImpl enderecoService) {
        this.enderecoService = enderecoService;
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<List<EnderecoDTO>> findAll() {
        logger.info("Endpoint findAll chamado");

        List<EnderecoDTO> list = enderecoService.findAll();

        return ResponseEntity.ok(list);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<EnderecoDTO> findById(UUID id) {
        logger.info("Endpoint findById chamado para o UUID: {}", id);

        EnderecoDTO enderecoDTO = enderecoService.findById(id);

        return ResponseEntity.ok(enderecoDTO);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<EnderecoDTO> insert(@Valid @RequestBody EnderecoDTO enderecoDTO) {
        logger.info("Endpoint insert chamado");

        EnderecoDTO insertedEndereco = enderecoService.insert(enderecoDTO);

        URI uri = URI.create("/api/endereco/" + insertedEndereco.enderecoUuid());
        return ResponseEntity.created(uri).body(insertedEndereco);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<EnderecoDTO> update(UUID id, @Valid @RequestBody EnderecoDTO enderecoDTO) {
        logger.info("Endpoint update chamado para o UUID: {}", id);

        try {
            EnderecoDTO endereco = enderecoService.update(id, enderecoDTO);
            return ResponseEntity.ok(endereco);
        } catch (ResourceNotFoundException e) {
            logger.error("Endereço não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<Void> delete(UUID uuid) {
        logger.info("Endpoint delete chamado para o UUID: {}", uuid);

        try {
            enderecoService.delete(uuid);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Endereço não encontrado: {}", uuid);
            return ResponseEntity.notFound().build();
        }
    }
}
