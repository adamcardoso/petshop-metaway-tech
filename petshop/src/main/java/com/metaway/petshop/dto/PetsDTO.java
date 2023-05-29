package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Pets;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

public record PetsDTO(
        @Schema(description = "Código de identificação do pet")
        UUID petsUuid,
        @Schema(description = "ID do cliente associado ao pet")
        UUID clienteId,
        @Schema(description = "ID da raça do pet")
        UUID racaId,
        @Schema(description = "Data de nascimento do pet")
        LocalDate dataDeNascimentoDoPet,
        @Schema(description = "Nome do pet")
        String nomeDoPet
) {
    public PetsDTO(Pets pets) {
        this(
                pets.getPetsUuid(),
                pets.getCliente().getClienteUuid(),
                pets.getRaca().getRacaUuid(),
                pets.getDataDeNascimentoDoPet(),
                pets.getNomeDoPet()
        );
    }

    public Pets toEntity() {
        Pets entity = new Pets();
        entity.setPetsUuid(petsUuid);
        entity.setClienteId(clienteId);
        entity.setRacaId(racaId);
        entity.setDataDeNascimentoDoPet(dataDeNascimentoDoPet);
        entity.setNomeDoPet(nomeDoPet);
        return entity;
    }
}