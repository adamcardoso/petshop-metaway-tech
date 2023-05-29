package com.metaway.petshop.api;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import com.metaway.petshop.services.UsuarioServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.RolesAllowed;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class UsuarioControllerImpl implements UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioControllerImpl.class);
    private final UsuarioServiceImpl usuarioServiceImpl;

    public UsuarioControllerImpl(UsuarioServiceImpl usuarioServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        logger.info("Endpoint findAll chamado");
        List<UsuarioDTO> list = usuarioServiceImpl.findAll();
        return ResponseEntity.ok().body(list);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<UsuarioDTO> findById(UUID id) {
        logger.info("Endpoint findById chamado para o UUID: {}", id);
        UsuarioDTO dto = usuarioServiceImpl.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<UsuarioDTO> insert(UsuarioInsertDTO dto) {
        logger.info("Endpoint insert chamado");
        UsuarioDTO newDto = usuarioServiceImpl.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getUsuarioUuid()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<UsuarioDTO> update(UUID id, UsuarioUpdateDTO dto) {
        logger.info("Endpoint update chamado para o UUID: {}", id);
        UsuarioDTO newDto = usuarioServiceImpl.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @RolesAllowed("ADMIN")
    @Override
    public ResponseEntity<Void> delete(UUID id) {
        logger.info("Endpoint delete chamado para o UUID: {}", id);
        usuarioServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}