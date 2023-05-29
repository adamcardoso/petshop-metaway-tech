package com.metaway.petshop.api.interfaces;

import com.metaway.petshop.dto.ContatoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface ContatoController {

    @Operation(description = "API para buscar todos os contatos")
    @GetMapping("/contatos")
    ResponseEntity<List<ContatoDTO>> findAll();

    @Operation(description = "API para buscar contato por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK do contato encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/contatos/{id}")
    ResponseEntity<ContatoDTO> findById(@PathVariable UUID id);

    @Operation(description = "API para inserir um novo contato")
    @PostMapping("/contatos")
    ResponseEntity<ContatoDTO> insert(@Valid @RequestBody ContatoDTO contatoDTO);

    @Operation(description = "API para atualizar um contato existente")
    @PutMapping("/contatos/{id}")
    ResponseEntity<ContatoDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ContatoDTO contato);

    @Operation(description = "API para excluir um contato")
    @DeleteMapping("/contatos/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}