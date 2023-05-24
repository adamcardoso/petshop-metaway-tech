package com.metaway.petshop.repositories;


import com.metaway.petshop.entities.Pets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PetsRepository extends JpaRepository<Pets, UUID> {

}
