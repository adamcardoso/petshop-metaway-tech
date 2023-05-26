package com.metaway.petshop.api;

import com.metaway.petshop.dto.ClienteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface ClienteController {

    @Operation(description = "API para buscar todas as pessoas")
    @GetMapping("/cliente")
    ResponseEntity<List<ClienteDTO>> findAll();

    @Operation(description = "API para buscar pessoa por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK da pessoa encontrada"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @GetMapping(value = "/cliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClienteDTO> findById(@PathVariable("id") UUID uuid);

    @Operation(description = "API para buscar pessoa por nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK da Lista de pessoas"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @Parameters(value = {@Parameter(name = "name", in = ParameterIn.QUERY)})
    @GetMapping(value = "/cliente/search", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<ClienteDTO>> findByName(@RequestParam("name") String name);

    @Operation(description = "API para inserir uma pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorno OK com a pessoa criada"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @PostMapping(value = "/cliente", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ClienteDTO> insert(@Valid @RequestBody ClienteDTO dto);

    @Operation(description = "API para atualizar uma pessoa")
    @PutMapping(value = "/cliente/{id}")
    ResponseEntity<ClienteDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ClienteDTO dto);

    @Operation(description = "API para excluir uma pessoa")
    @DeleteMapping(value = "/cliente/{uuid}")
    ResponseEntity<Void> delete(@PathVariable UUID uuid);
}


