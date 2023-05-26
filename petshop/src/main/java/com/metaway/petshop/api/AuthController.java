package com.metaway.petshop.api;

import com.metaway.petshop.dto.Login;
import com.metaway.petshop.dto.UsuarioDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.validation.Valid;

public interface AuthController {

    @Operation(description = "Faz login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno OK para login"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @PostMapping("/login")
    ResponseEntity<UsuarioDTO> login(@Valid @RequestBody Login login);

    @Operation(description = "Faz logout")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Retorno OK para logout"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "500", description = "Erro inesperado")
    })
    @PostMapping(value = "/logout")
    ResponseEntity<Void> logout(@RequestHeader("Authorization") String token);
}
