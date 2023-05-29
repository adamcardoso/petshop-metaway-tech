package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.AtendimentoController;
import com.metaway.petshop.dto.AtendimentoDTO;
import com.metaway.petshop.services.impl.AtendimentoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AtendimentoControllerImpl implements AtendimentoController {

    private static final Logger logger = LoggerFactory.getLogger(AtendimentoControllerImpl.class);

    private final AtendimentoServiceImpl atendimentoServiceImpl;

    public AtendimentoControllerImpl(AtendimentoServiceImpl atendimentoServiceImpl) {
        this.atendimentoServiceImpl = atendimentoServiceImpl;
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<List<AtendimentoDTO>> findAll() {
        logger.info("Endpoint findAll chamado");

        List<AtendimentoDTO> list = atendimentoServiceImpl.findAll();

        return ResponseEntity.ok(list);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<AtendimentoDTO> findById(UUID id) {
        logger.info("Endpoint findById chamado para o UUID: {}", id);

        AtendimentoDTO atendimentoDTO = atendimentoServiceImpl.findById(id);

        return ResponseEntity.ok(atendimentoDTO);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<AtendimentoDTO> insert(@Valid @RequestBody AtendimentoDTO atendimentoDTO) {
        logger.info("Endpoint insert chamado");

        AtendimentoDTO insertedAtendimento = atendimentoServiceImpl.insert(atendimentoDTO);

        URI uri = URI.create("/api/atendimentos/" + insertedAtendimento.atendimentoUuid());
        return ResponseEntity.created(uri).body(insertedAtendimento);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<AtendimentoDTO> update(UUID id, @Valid @RequestBody AtendimentoDTO atendimentoDTO) {
        logger.info("Endpoint update chamado para o UUID: {}", id);

        AtendimentoDTO updatedAtendimento = atendimentoServiceImpl.update(id, atendimentoDTO);

        return ResponseEntity.ok(updatedAtendimento);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<Void> delete(UUID id) {
        logger.info("Endpoint delete chamado para o UUID: {}", id);

        atendimentoServiceImpl.delete(id);

        return ResponseEntity.noContent().build();
    }
}
