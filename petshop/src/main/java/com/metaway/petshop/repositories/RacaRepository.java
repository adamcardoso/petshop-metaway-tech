package com.metaway.petshop.repositories;


import com.metaway.petshop.entities.Raca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RacaRepository extends JpaRepository<Raca, UUID> {

}
