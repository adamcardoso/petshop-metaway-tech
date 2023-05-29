package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.ClienteController;
import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.impl.ClienteServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class ClienteControllerImpl implements ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteControllerImpl.class);

    private final ClienteServiceImpl clienteServiceImpl;

    public ClienteControllerImpl(ClienteServiceImpl clienteServiceImpl) {
        this.clienteServiceImpl = clienteServiceImpl;
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<List<ClienteDTO>> findAll() {
        logger.info("Endpoint findAll chamado");
        List<ClienteDTO> list = clienteServiceImpl.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<ClienteDTO> findById(UUID uuid) {
        logger.info("Endpoint findById chamado para o UUID: {}", uuid);
        Optional<ClienteDTO> clienteDTO = clienteServiceImpl.findById(uuid);
        if (clienteDTO.isPresent()) {
            return ResponseEntity.ok(clienteDTO.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado");
        }
    }

    @RolesAllowed({"CLIENTE", "ADMIN"})
    @Override
    public ResponseEntity<List<ClienteDTO>> findByName(String name) {
        logger.info("Endpoint findByName chamado para o nome: {}", name);
        try {
            List<ClienteDTO> clienteDTOs = clienteServiceImpl.findByName(name);
            return ResponseEntity.ok().body(clienteDTOs);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<ClienteDTO> insert(@Valid @RequestBody ClienteDTO dto) {
        logger.info("Endpoint insert chamado");
        dto = clienteServiceImpl.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.cpf()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<ClienteDTO> update(UUID id, @Valid @RequestBody ClienteDTO dto) {
        logger.info("Endpoint update chamado para o UUID: {}", id);
        dto = clienteServiceImpl.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<Void> delete(UUID uuid) {
        logger.info("Endpoint delete chamado para o UUID: {}", uuid);
        clienteServiceImpl.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
