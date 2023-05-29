package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Contato;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record ContatoDTO(
        @Schema(description = "Código de identificação do contato")
        UUID contatoUuid,

        @Schema(description = "Tag do contato")
        String tag,

        @Schema(description = "Email do contato")
        String email,

        @Schema(description = "Telefone do contato")
        String telefone,

        @Schema(description = "Valor do contato")
        String valor,
        @Schema(description = "ID do cliente associado ao contato")
        UUID clienteId
) {
    public ContatoDTO(Contato contato) {
        this(
                contato.getContatoUuid(),
                contato.getTag(),
                contato.getEmail(),
                contato.getTelefone(),
                contato.getValor(),
                contato.getCliente().getClienteUuid()
        );
    }
}


