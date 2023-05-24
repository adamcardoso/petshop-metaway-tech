package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Pets;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetsDTO {
    private UUID petsUuid;
    private UUID clienteId;
    private UUID racaId;
    private LocalDate dataDeNascimentoDoPet;
    private String nomeDoPet;

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
