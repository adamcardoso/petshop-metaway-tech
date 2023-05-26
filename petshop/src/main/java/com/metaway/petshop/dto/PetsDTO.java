package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Pets;

import java.time.LocalDate;
import java.util.UUID;

public record PetsDTO(
        UUID petsUuid,
        UUID clienteId,
        UUID racaId,
        LocalDate dataDeNascimentoDoPet,
        String nomeDoPet
) {
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
