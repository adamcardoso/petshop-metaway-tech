package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Atendimento;
import com.metaway.petshop.entities.Pets;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record AtendimentoDTO(
        @Schema(description = "Código de identificação do atendimento")
        UUID atendimentoUuid,

        @Schema(description = "Pet atendido")
        Pets pet,

        @Schema(description = "Descrição do atendimento")
        String descricaoDoAtendimento,

        @Schema(description = "Valor do atendimento")
        String valorDoAtendimento,
        @Schema(description = "Data do atendimento")
        String dataDoAtendimento
) {
        public AtendimentoDTO(Atendimento atendimento) {
                this(
                        atendimento.getAtendimentoUuid(),
                        atendimento.getPet(),
                        atendimento.getDescricaoDoAtendimento(),
                        atendimento.getValorDoAtendimento(),
                        atendimento.getDataDoAtendimento()
                );
        }
}
