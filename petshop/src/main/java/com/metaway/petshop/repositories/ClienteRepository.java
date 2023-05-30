package com.metaway.petshop.repositories;

import com.metaway.petshop.entities.Cliente;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    List<Cliente> findByNomeDoClienteContainingIgnoreCase(String nomeDoCliente);

    Optional<Cliente> findByCpf(String cpf);
}
