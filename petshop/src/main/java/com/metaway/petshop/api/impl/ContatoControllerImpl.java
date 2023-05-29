package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.ContatoController;
import com.metaway.petshop.dto.ContatoDTO;
import com.metaway.petshop.services.impl.ContatoServiceImpl;
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
public class ContatoControllerImpl implements ContatoController {

    private static final Logger logger = LoggerFactory.getLogger(ContatoControllerImpl.class);

    private final ContatoServiceImpl contatoServiceImpl;

    public ContatoControllerImpl(ContatoServiceImpl contatoServiceImpl) {
        this.contatoServiceImpl = contatoServiceImpl;
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<List<ContatoDTO>> findAll() {
        logger.info("Endpoint findAll chamado");

        List<ContatoDTO> list = contatoServiceImpl.findAll();

        return ResponseEntity.ok(list);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<ContatoDTO> findById(UUID id) {
        logger.info("Endpoint findById chamado para o UUID: {}", id);

        ContatoDTO atendimentoDTO = contatoServiceImpl.findById(id);

        return ResponseEntity.ok(atendimentoDTO);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<ContatoDTO> insert(@Valid @RequestBody ContatoDTO contatoDTO) {
        logger.info("Endpoint insert chamado");

        ContatoDTO insertedContato = contatoServiceImpl.insert(contatoDTO);

        URI uri = URI.create("/api/atendimentos/" + insertedContato.contatoUuid());
        return ResponseEntity.created(uri).body(insertedContato);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<ContatoDTO> update(UUID id, @Valid @RequestBody ContatoDTO contatoDTO) {
        logger.info("Endpoint update chamado para o UUID: {}", id);

        ContatoDTO updatedContato = contatoServiceImpl.update(id, contatoDTO);

        return ResponseEntity.ok(updatedContato);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<Void> delete(UUID id) {
        logger.info("Endpoint delete chamado para o UUID: {}", id);

        contatoServiceImpl.delete(id);

        return ResponseEntity.noContent().build();
    }
}
