package com.metaway.petshop.api;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import com.metaway.petshop.services.UsuarioServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
public class UsuarioControllerImpl implements UsuarioController {
    private final UsuarioServiceImpl usuarioServiceImpl;

    public UsuarioControllerImpl(UsuarioServiceImpl usuarioServiceImpl) {
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @Override
    public ResponseEntity<List<UsuarioDTO>> findAll() {
        List<UsuarioDTO> list = usuarioServiceImpl.findAll();
        return ResponseEntity.ok().body(list);
    }

    @Override
    public ResponseEntity<UsuarioDTO> findById(@PathVariable UUID id) {
        UsuarioDTO dto = usuarioServiceImpl.findById(id);
        return ResponseEntity.ok().body(dto);
    }

    @Override
    public ResponseEntity<UsuarioDTO> insert(@RequestBody @Valid UsuarioInsertDTO dto) {
        UsuarioDTO newDto = usuarioServiceImpl.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newDto.getUsuarioUuid()).toUri();
        return ResponseEntity.created(uri).body(newDto);
    }

    @Override
    public ResponseEntity<UsuarioDTO> update(@PathVariable UUID id, @RequestBody @Valid UsuarioUpdateDTO dto) {
        UsuarioDTO newDto = usuarioServiceImpl.update(id, dto);
        return ResponseEntity.ok().body(newDto);
    }

    @Override
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        usuarioServiceImpl.delete(id);
        return ResponseEntity.noContent().build();
    }
}

