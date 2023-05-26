package com.metaway.petshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

public record RacaDTO(
        UUID racaUuid,
        String descricao
) {

}

