package com.metaway.petshop.api.interfaces;

import com.metaway.petshop.dto.RacaDTO;
import com.metaway.petshop.entities.Raca;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface RacaController {

    @Operation(description = "API para buscar todas as raças")
    @GetMapping("/racas")
    ResponseEntity<List<RacaDTO>> findAll();

    @Operation(description = "API para buscar raça por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK da raça encontrada"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/racas/{id}")
    ResponseEntity<RacaDTO> findById(@PathVariable UUID id);

    @Operation(description = "API para inserir uma nova raça")
    @PostMapping("/racas")
    ResponseEntity<RacaDTO> insert(@Valid @RequestBody RacaDTO racaDTO);

    @Operation(description = "API para atualizar uma raça existente")
    @PutMapping("/racas/{id}")
    ResponseEntity<RacaDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody RacaDTO raca);

    @Operation(description = "API para excluir uma raça")
    @DeleteMapping("/racas/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}