package com.metaway.petshop;

import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.repositories.ClienteRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = ClienteRepository.class)
@EntityScan(basePackageClasses = Cliente.class)
public class PetshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
	}

}
