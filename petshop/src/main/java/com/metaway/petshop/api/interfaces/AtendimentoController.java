package com.metaway.petshop.api.interfaces;

import com.metaway.petshop.dto.AtendimentoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface AtendimentoController {

    @Operation(description = "API para buscar todos os atendimentos")
    @GetMapping("/atendimentos")
    ResponseEntity<List<AtendimentoDTO>> findAll();

    @Operation(description = "API para buscar atendimento por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK do atendimento encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/atendimentos/{id}")
    ResponseEntity<AtendimentoDTO> findById(@PathVariable UUID id);

    @Operation(description = "API para inserir um novo atendimento")
    @PostMapping("/atendimentos")
    ResponseEntity<AtendimentoDTO> insert(@Valid @RequestBody AtendimentoDTO atendimentoDTO);

    @Operation(description = "API para atualizar um atendimento existente")
    @PutMapping("/atendimentos/{id}")
    ResponseEntity<AtendimentoDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody AtendimentoDTO dto);

    @Operation(description = "API para excluir um atendimento")
    @DeleteMapping("/atendimentos/{uuid}")
    ResponseEntity<Void> delete(@PathVariable UUID uuid);
}