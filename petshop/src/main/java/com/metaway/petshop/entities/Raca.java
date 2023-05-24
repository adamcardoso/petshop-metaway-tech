package com.metaway.petshop.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "racaUuid") // Alterado para racaUuid
@Entity
@Table(name = "tb_raca")
public class Raca implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Código de identificação do pet")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "raca_uuid") // Adicionado para renomear a coluna no banco de dados
    private UUID racaUuid; // Alterado para racaUuid

    private String descricao;
}
