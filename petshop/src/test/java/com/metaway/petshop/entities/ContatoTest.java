package com.metaway.petshop.entities;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ContatoTest {

    @Test
    public void testEntities() {
        // Criação de objeto Contato
        Contato contato = new Contato();
        UUID contatoUuid = UUID.randomUUID();
        contato.setContatoUuid(contatoUuid);
        contato.setTag("Tag do Contato");
        contato.setEmail("contato@example.com");
        contato.setTelefone("1234567890");
        contato.setValor("Valor do Contato");

        // Verificações dos atributos do Contato
        Assertions.assertEquals(contatoUuid, contato.getContatoUuid());
        Assertions.assertEquals("Tag do Contato", contato.getTag());
        Assertions.assertEquals("contato@example.com", contato.getEmail());
        Assertions.assertEquals("1234567890", contato.getTelefone());
        Assertions.assertEquals("Valor do Contato", contato.getValor());

        // Verificação da associação com Cliente (opcional)
        Cliente cliente = new Cliente();
        UUID clienteUuid = UUID.randomUUID();
        cliente.setClienteUuid(clienteUuid);
        contato.setCliente(cliente);
        Assertions.assertEquals(cliente, contato.getCliente());
    }
}
