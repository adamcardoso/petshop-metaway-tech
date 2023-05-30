package com.metaway.petshop.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class PetsTest {

    @Test
    public void testEntities() {
        // Criação de objeto Pets
        Pets pets = new Pets();
        UUID petsUuid = UUID.randomUUID();
        pets.setPetsUuid(petsUuid);

        Cliente cliente = new Cliente();
        UUID clienteUuid = UUID.randomUUID();
        cliente.setClienteUuid(clienteUuid);
        pets.setCliente(cliente);

        Raca raca = new Raca();
        UUID racaUuid = UUID.randomUUID();
        raca.setRacaUuid(racaUuid);
        pets.setRaca(raca);

        LocalDate dataDeNascimento = LocalDate.of(2020, 1, 1);
        pets.setDataDeNascimentoDoPet(dataDeNascimento);

        pets.setNomeDoPet("Rex");

        // Verificações dos atributos do Pets
        Assertions.assertEquals(petsUuid, pets.getPetsUuid());
        Assertions.assertEquals(cliente, pets.getCliente());
        Assertions.assertEquals(raca, pets.getRaca());
        Assertions.assertEquals(dataDeNascimento, pets.getDataDeNascimentoDoPet());
        Assertions.assertEquals("Rex", pets.getNomeDoPet());
    }

    @Test
    public void testSetClienteId() {
        Pets pets = new Pets();
        UUID clienteUuid = UUID.randomUUID();
        pets.setClienteId(clienteUuid);

        Cliente cliente = pets.getCliente();
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals(clienteUuid, cliente.getClienteUuid());
    }

    @Test
    public void testSetRacaId() {
        Pets pets = new Pets();
        UUID racaUuid = UUID.randomUUID();
        pets.setRacaId(racaUuid);

        Raca raca = pets.getRaca();
        Assertions.assertNotNull(raca);
        Assertions.assertEquals(racaUuid, raca.getRacaUuid());
    }
}
