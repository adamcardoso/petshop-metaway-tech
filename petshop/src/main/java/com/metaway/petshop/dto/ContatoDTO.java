package com.metaway.petshop.dto;

import java.util.UUID;

public record ContatoDTO(
        UUID contatoUuid,
        String tag,
        String email,
        String telefone,
        String valor,
        UUID clienteId
) {

}

