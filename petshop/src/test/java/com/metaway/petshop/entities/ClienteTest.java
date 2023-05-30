package com.metaway.petshop.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

public class ClienteTest {

    @Test
    public void testEntities() {
        // Criação de objetos relacionados
        Cliente cliente = new Cliente();
        UUID clienteUuid = UUID.randomUUID();
        cliente.setClienteUuid(clienteUuid);
        cliente.setNomeDoCliente("João");
        cliente.setCpf("12345678901");
        cliente.setDataDeCadastro(LocalDate.now());

        Endereco endereco = new Endereco();
        UUID enderecoUuid = UUID.randomUUID();
        endereco.setEnderecoUuid(enderecoUuid);
        endereco.setCliente(cliente);
        endereco.setLogradouro("Rua A");
        endereco.setCidade("Cidade A");

        Raca raca = new Raca();
        UUID racaUuid = UUID.randomUUID();
        raca.setRacaUuid(racaUuid);
        raca.setDescricao("Rottweiler");

        Pets pet = new Pets();
        UUID petUuid = UUID.randomUUID();
        pet.setPetsUuid(petUuid);
        pet.setCliente(cliente);
        pet.setRaca(raca);
        pet.setDataDeNascimentoDoPet(LocalDate.of(2019, 5, 15));
        pet.setNomeDoPet("Rex");

        // Verificações dos atributos das entidades
        Assertions.assertEquals(clienteUuid, cliente.getClienteUuid());
        Assertions.assertEquals("João", cliente.getNomeDoCliente());
        Assertions.assertEquals("12345678901", cliente.getCpf());
        Assertions.assertEquals(LocalDate.now(), cliente.getDataDeCadastro());

        Assertions.assertEquals(enderecoUuid, endereco.getEnderecoUuid());
        Assertions.assertEquals(cliente, endereco.getCliente());
        Assertions.assertEquals("Rua A", endereco.getLogradouro());
        Assertions.assertEquals("Cidade A", endereco.getCidade());

        Assertions.assertEquals(racaUuid, raca.getRacaUuid());
        Assertions.assertEquals("Rottweiler", raca.getDescricao());

        Assertions.assertEquals(petUuid, pet.getPetsUuid());
        Assertions.assertEquals(cliente, pet.getCliente());
        Assertions.assertEquals(raca, pet.getRaca());
        Assertions.assertEquals(LocalDate.of(2019, 5, 15), pet.getDataDeNascimentoDoPet());
        Assertions.assertEquals("Rex", pet.getNomeDoPet());
    }
}


