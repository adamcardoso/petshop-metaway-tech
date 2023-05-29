package com.metaway.petshop.api.interfaces;

import com.metaway.petshop.dto.EnderecoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface EnderecoController {

    @Operation(description = "API para buscar todos os endereços")
    @GetMapping("/enderecos")
    ResponseEntity<List<EnderecoDTO>> findAll();

    @Operation(description = "API para buscar endereço por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK do endereço encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/enderecos/{id}")
    ResponseEntity<EnderecoDTO> findById(@PathVariable UUID id);

    @Operation(description = "API para inserir um novo endereço")
    @PostMapping("/enderecos")
    ResponseEntity<EnderecoDTO> insert(@Valid @RequestBody EnderecoDTO endereco);

    @Operation(description = "API para atualizar um endereço existente")
    @PutMapping("/enderecos/{id}")
    ResponseEntity<EnderecoDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody EnderecoDTO endereco);

    @Operation(description = "API para excluir um endereço")
    @DeleteMapping("/enderecos/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
