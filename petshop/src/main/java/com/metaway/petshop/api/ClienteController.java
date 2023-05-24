package com.metaway.petshop.api;

import com.metaway.petshop.dto.ClienteDTO;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.services.ClienteService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api")
@OpenAPIDefinition
@Tag(name = "/api", description = "Grupo de API's para manipulação de dados do cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(description = "API para buscar pessoas por id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno OK da Lista de transações"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @ResponseBody
    @GetMapping(value = "/cliente/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> findById(@PathVariable("id") UUID uuid) {
        Optional<ClienteDTO> clienteDTO = clienteService.findById(uuid);
        if (clienteDTO.isPresent()) {
            return ResponseEntity.ok(clienteDTO.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Recurso não encontrado");
        }
    }

    @Operation(description = "API para buscar os transações por nome da pessoa")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno OK da Lista de transações"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @ResponseBody
    @GetMapping(value = "/cliente/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ClienteDTO>> findByName(@RequestParam("name") String name) {
        try {
            List<ClienteDTO> clienteDTOs = clienteService.findByName(name);

            return ResponseEntity.ok().body(clienteDTOs);
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(description = "API para inserir dados de uma pessoa no banco")
    @ResponseBody
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Retorno OK com a transação criada."),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")
    })
    @PostMapping(value = "/cliente", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ClienteDTO> insert(@Valid @RequestBody ClienteDTO dto) {
        dto = clienteService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getCpf()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping(value = "/cliente/{id}")
    public ResponseEntity<ClienteDTO> update(@PathVariable("id") UUID id, @Valid @RequestBody ClienteDTO dto) {
        dto = clienteService.update(id, dto);
        return ResponseEntity.ok().body(dto);
    }


    @DeleteMapping(value = "/cliente/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable UUID uuid) {
        clienteService.delete(uuid);
        return ResponseEntity.noContent().build();
    }
}
