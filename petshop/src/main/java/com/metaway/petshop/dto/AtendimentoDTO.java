package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Pets;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record AtendimentoDTO(
        @Schema(description = "Código de identificação do atendimento")
        UUID atendimentoUuid,
        Pets pet,
        String descricaoDoAtendimento,
        String valorDoAtendimento,
        @Schema(description = "Data do atendimento")
        String dataDoAtendimento
) {

}
