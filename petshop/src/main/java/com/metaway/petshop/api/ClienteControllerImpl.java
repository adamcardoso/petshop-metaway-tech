package com.metaway.petshop.api;

import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class ClienteControllerImpl implements ClienteController {

    private final ClienteService clienteService;

    public ClienteControllerImpl(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> list = clienteService.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Override
    public ResponseEntity<ClienteDTO> findById(UUID uuid) {
        Optional<ClienteDTO> clienteDTO = clienteService.findById(uuid);
        if (clienteDTO.isPresent()) {
            return ResponseEntity.ok(clienteDTO.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado");
        }
    }

    @Override
    public ResponseEntity<List<ClienteDTO>> findByName(String name) {
        try {
            List<ClienteDTO> clienteDTOs = clienteService.findByName(name);
            return ResponseEntity.ok().body(clienteDTOs);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<ClienteDTO> insert(@Valid @RequestBody ClienteDTO dto) {
        dto = clienteService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.cpf()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @Override
    public ResponseEntity<ClienteDTO> update(UUID id, @Valid @RequestBody ClienteDTO dto) {
        dto = clienteService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<Void> delete(UUID uuid) {
        clienteService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
