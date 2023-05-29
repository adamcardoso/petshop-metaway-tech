package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Raca;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record RacaDTO(
        @Schema(description = "Código de identificação da raça")
        UUID racaUuid,
        @Schema(description = "Descrição da raça")
        String descricao
) {
    public RacaDTO(Raca raca) {
        this(
                raca.getRacaUuid(),
                raca.getDescricao()
        );
    }
}

