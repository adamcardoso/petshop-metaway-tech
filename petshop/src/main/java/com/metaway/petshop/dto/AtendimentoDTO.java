package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Pets;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AtendimentoDTO {

    @Schema(description = "Código de identificação do atendimento")
    private UUID atendimentoUuid;

    private Pets pet;

    private String descricaoDoAtendimento;

    private String valorDoAtendimento;

    @Schema(description = "Data do atendimento")
    private String dataDoAtendimento;
}
