package com.metaway.petshop.service;

import com.metaway.petshop.dto.EnderecoDTO;
import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.entities.Endereco;
import com.metaway.petshop.exceptions.DatabaseException;
import com.metaway.petshop.exceptions.ResourceNotFoundException;
import com.metaway.petshop.repositories.EnderecoRepository;
import com.metaway.petshop.services.impl.EnderecoServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.*;

public class EnderecoServiceImplTest {

    @Mock
    private EnderecoRepository enderecoRepository;

    @InjectMocks
    private EnderecoServiceImpl enderecoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindById() {
        // Mock do objeto Endereco retornado pelo repositório
        Endereco endereco = new Endereco();
        UUID enderecoUuid = UUID.randomUUID();
        UUID clienteUuid = UUID.randomUUID();
        endereco.setEnderecoUuid(enderecoUuid);

        Cliente cliente = new Cliente();
        cliente.setClienteUuid(clienteUuid);
        endereco.setCliente(cliente);

        Mockito.when(enderecoRepository.findById(enderecoUuid)).thenReturn(Optional.of(endereco));

        // Chamar o método findById do serviço
        EnderecoDTO resultado = enderecoService.findById(enderecoUuid);

        // Verificar se o resultado possui os valores esperados
        Assertions.assertEquals(enderecoUuid, resultado.enderecoUuid());
        Assertions.assertEquals(clienteUuid, resultado.clienteUuid());
    }

    @Test
    public void testInsert() {
        // Mock do objeto Endereco retornado pelo repositório após a inserção
        Endereco endereco = new Endereco();
        UUID enderecoUuid = UUID.randomUUID();
        UUID clienteUuid = UUID.randomUUID();
        endereco.setEnderecoUuid(enderecoUuid);
        endereco.setCliente(new Cliente());
        endereco.getCliente().setClienteUuid(clienteUuid);
        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(endereco);

        // Criar um objeto EnderecoDTO para a inserção
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, clienteUuid, "Rua Teste", "Cidade Teste", "Bairro Teste", "Complemento Teste", "Tag Teste");

        // Chamar o método insert do serviço
        EnderecoDTO resultado = enderecoService.insert(enderecoDTO);

        // Verificar se o resultado possui o UUID gerado pelo mock do repositório
        Assertions.assertEquals(enderecoUuid, resultado.enderecoUuid());
        Assertions.assertEquals(clienteUuid, resultado.clienteUuid());
    }

    @Test
    public void testUpdate() {
        // Mock do objeto Endereco retornado pelo repositório após a atualização
        Endereco endereco = new Endereco();
        UUID enderecoUuid = UUID.randomUUID();
        UUID clienteUuid = UUID.randomUUID();
        endereco.setEnderecoUuid(enderecoUuid);
        endereco.setCliente(new Cliente());
        endereco.getCliente().setClienteUuid(clienteUuid);
        Mockito.when(enderecoRepository.findById(enderecoUuid)).thenReturn(Optional.of(endereco));
        Mockito.when(enderecoRepository.save(Mockito.any(Endereco.class))).thenReturn(endereco);

        // Criar um objeto EnderecoDTO para a atualização
        EnderecoDTO enderecoDTO = new EnderecoDTO(null, clienteUuid, "Rua Atualizada", "Cidade Atualizada", "Bairro Atualizado", "Complemento Atualizado", "Tag Atualizada");

        // Chamar o método update do serviço
        EnderecoDTO resultado = enderecoService.update(enderecoUuid, enderecoDTO);

        // Verificar se o resultado possui o UUID gerado pelo mock do repositório
        Assertions.assertEquals(enderecoUuid, resultado.enderecoUuid());
        Assertions.assertEquals(clienteUuid, resultado.clienteUuid());
        Assertions.assertEquals(enderecoDTO.logradouro(), endereco.getLogradouro());
        Assertions.assertEquals(enderecoDTO.cidade(), endereco.getCidade());
        Assertions.assertEquals(enderecoDTO.bairro(), endereco.getBairro());
        Assertions.assertEquals(enderecoDTO.complemento(), endereco.getComplemento());
        Assertions.assertEquals(enderecoDTO.tag(), endereco.getTag());
    }

    @Test
    public void testUpdate_ThrowResourceNotFoundException() {
        UUID enderecoUuid = UUID.randomUUID();
        Mockito.when(enderecoRepository.findById(enderecoUuid)).thenReturn(Optional.empty());

        EnderecoDTO enderecoDTO = new EnderecoDTO(null, UUID.randomUUID(), "Rua Atualizada", "Cidade Atualizada", "Bairro Atualizado", "Complemento Atualizado", "Tag Atualizada");

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            enderecoService.update(enderecoUuid, enderecoDTO);
        });
    }

    @Test
    public void testDelete() {
        UUID enderecoUuid = UUID.randomUUID();
        Mockito.when(enderecoRepository.existsById(enderecoUuid)).thenReturn(true);

        // Chamar o método delete do serviço
        Assertions.assertDoesNotThrow(() -> {
            enderecoService.delete(enderecoUuid);
        });

        // Verificar se o repositório deletou o endereço com o UUID fornecido
        Mockito.verify(enderecoRepository, Mockito.times(1)).deleteById(enderecoUuid);
    }

    @Test
    public void testDelete_ThrowResourceNotFoundException() {
        UUID enderecoUuid = UUID.randomUUID();
        Mockito.when(enderecoRepository.existsById(enderecoUuid)).thenReturn(false);

        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            enderecoService.delete(enderecoUuid);
        });
    }

    @Test
    public void testDelete_ThrowDatabaseException() {
        UUID enderecoUuid = UUID.randomUUID();
        Mockito.when(enderecoRepository.existsById(enderecoUuid)).thenReturn(true);
        Mockito.doThrow(DataIntegrityViolationException.class).when(enderecoRepository).deleteById(enderecoUuid);

        Assertions.assertThrows(DatabaseException.class, () -> {
            enderecoService.delete(enderecoUuid);
        });
    }
}
