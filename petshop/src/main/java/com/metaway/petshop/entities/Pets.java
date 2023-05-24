package com.metaway.petshop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "uuid")
@Entity
@Table(name = "tb_pets")
public class Pets implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Código de identificação do pet")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "raca_id")
    private Raca raca;

    @Schema(description = "Data de nascimento do pet")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDeNascimentoDoPet;

    @Schema(description = "Nome do pet")
    private String nomeDoPet;
}
