package com.metaway.petshop.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.metaway.petshop.entities.Cliente;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ClienteDTO(
        @Schema(description = "Código de identificação do cliente")
        UUID clienteUuid,
        @Schema(description = "Nome do cliente")
        String nomeDoCliente,
        @Schema(description = "CPF do cliente")
        String cpf,
        @Schema(description = "Data de cadastro do cliente")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        @JsonSerialize(using = LocalDateSerializer.class)
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        LocalDate dataDeCadastro,

        @Schema(description = "Lista de endereços do cliente")
        List<EnderecoDTO> enderecos,

        @Schema(description = "Lista de pets do cliente")
        List<PetsDTO> pets
) {
    public ClienteDTO(Cliente cliente) {
        this(
                cliente.getClienteUuid(),
                cliente.getNomeDoCliente(),
                cliente.getCpf(),
                cliente.getDataDeCadastro(),
                cliente.getEnderecos().stream()
                        .map(EnderecoDTO::fromEntity)
                        .collect(Collectors.toList()),
                cliente.getPets().stream()
                        .map(PetsDTO::fromEntity)
                        .collect(Collectors.toList())
        );
    }

    public static Cliente toEntity(ClienteDTO dto) {
        return new Cliente(
                dto.clienteUuid(),
                dto.nomeDoCliente(),
                dto.cpf(),
                dto.dataDeCadastro(),
                dto.enderecos.stream()
                        .map(EnderecoDTO::toEntity)
                        .collect(Collectors.toList()),
                dto.pets.stream()
                        .map(PetsDTO::toEntity)
                        .collect(Collectors.toList())
        );
    }
}
