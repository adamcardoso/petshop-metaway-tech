package com.metaway.petshop.api.interfaces;

import com.metaway.petshop.dto.PetsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface PetsController {

    @Operation(description = "API para buscar todos os pets")
    @GetMapping("/pets")
    List<PetsDTO> findAll();

    @Operation(description = "API para buscar pet por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK do pet encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/pets/{id}")
    ResponseEntity<PetsDTO> findById(@PathVariable UUID id);

    @Operation(description = "API para inserir um novo pet")
    @PostMapping("/pets")
    ResponseEntity<PetsDTO> insert(@Valid @RequestBody PetsDTO petsDTO);

    @Operation(description = "API para atualizar um pet existente")
    @PutMapping("/pets/{id}")
    ResponseEntity<PetsDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody PetsDTO pet);

    @Operation(description = "API para excluir um pet")
    @DeleteMapping("/pets/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}