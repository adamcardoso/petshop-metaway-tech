package com.metaway.petshop.api;

import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.dto.UsuarioInsertDTO;
import com.metaway.petshop.dto.UsuarioUpdateDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

public interface UsuarioController {

    @Operation(description = "API para buscar todos os usuários")
    @GetMapping("/usuario")
    ResponseEntity<List<UsuarioDTO>> findAll();

    @Operation(description = "API para buscar usuário por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK do usuário encontrado"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @GetMapping("/usuario/{id}")
    ResponseEntity<UsuarioDTO> findById(@PathVariable UUID id);

    @Operation(description = "API para inserir um novo usuário")
    @PostMapping("/usuario")
    ResponseEntity<UsuarioDTO> insert(@RequestBody @Valid UsuarioInsertDTO dto);

    @Operation(description = "API para atualizar um usuário")
    @PutMapping("/usuario/{id}")
    ResponseEntity<UsuarioDTO> update(@PathVariable UUID id, @RequestBody @Valid UsuarioUpdateDTO dto);

    @Operation(description = "API para excluir um usuário")
    @DeleteMapping("/usuario/{id}")
    ResponseEntity<Void> delete(@PathVariable UUID id);
}
