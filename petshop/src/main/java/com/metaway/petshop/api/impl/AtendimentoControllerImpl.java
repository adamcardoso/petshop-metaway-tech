package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.AtendimentoController;
import com.metaway.petshop.dto.AtendimentoDTO;
import com.metaway.petshop.services.interfaces.AtendimentoService;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.exceptions.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AtendimentoControllerImpl implements AtendimentoController {

    private static final Logger logger = LoggerFactory.getLogger(AtendimentoControllerImpl.class);

    private final AtendimentoService atendimentoService;

    public AtendimentoControllerImpl(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @Override
    public ResponseEntity<List<AtendimentoDTO>> findAll() {
        logger.info("Endpoint findAll chamado");

        List<AtendimentoDTO> atendimentos = atendimentoService.findAll();

        return ResponseEntity.ok(atendimentos);
    }

    @Override
    public ResponseEntity<AtendimentoDTO> findById(UUID id) {
        logger.info("Endpoint findById chamado para o UUID: {}", id);

        try {
            AtendimentoDTO atendimentoDTO = atendimentoService.findById(id);
            return ResponseEntity.ok(atendimentoDTO);
        } catch (ResourceNotFoundException e) {
            logger.error("Atendimento não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<AtendimentoDTO> insert(@Valid @RequestBody AtendimentoDTO atendimentoDTO) {
        logger.info("Endpoint insert chamado");

        AtendimentoDTO insertedAtendimento = atendimentoService.insert(atendimentoDTO);

        URI uri = URI.create("/api/atendimentos/" + insertedAtendimento.atendimentoUuid());
        return ResponseEntity.created(uri).body(insertedAtendimento);
    }

    @Override
    public ResponseEntity<AtendimentoDTO> update(UUID id, @Valid @RequestBody AtendimentoDTO atendimentoDTO) {
        logger.info("Endpoint update chamado para o UUID: {}", id);

        try {
            AtendimentoDTO updatedAtendimento = atendimentoService.update(id, atendimentoDTO);
            return ResponseEntity.ok(updatedAtendimento);
        } catch (ResourceNotFoundException e) {
            logger.error("Atendimento não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Void> delete(UUID id) {
        logger.info("Endpoint delete chamado para o UUID: {}", id);

        try {
            atendimentoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            logger.error("Atendimento não encontrado: {}", id);
            return ResponseEntity.notFound().build();
        } catch (DatabaseException e) {
            logger.error("Erro ao deletar atendimento: {}", id);
            return ResponseEntity.status(500).build();
        }
    }
}
