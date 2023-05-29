package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Contato;

import java.util.UUID;

public record ContatoDTO(
        UUID contatoUuid,
        String tag,
        String email,
        String telefone,
        String valor,
        UUID clienteId
) {

    public ContatoDTO(Contato contato) {
        this(contato.getContatoUuid(), contato.getTag(), contato.getEmail(), contato.getTelefone(), contato.getValor(),
                contato.getCliente().getClienteUuid());
    }

}

