package com.metaway.petshop.entities;

import com.metaway.petshop.dto.AtendimentoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "atendimentoUuid") // Alterado para atendimentoUuid
@Entity
@Table(name = "tb_atendimento")
public class Atendimento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Código de identificação do atendimento")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID atendimentoUuid; // Alterado para atendimentoUuid

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pets pet;

    private String descricaoDoAtendimento;

    private String valorDoAtendimento;

    @Schema(description = "Data do atendimento")
    private String dataDoAtendimento;

    public Atendimento(AtendimentoDTO atendimentoDTO) {
        this.atendimentoUuid = atendimentoDTO.atendimentoUuid();
        this.pet = atendimentoDTO.pet();
        this.descricaoDoAtendimento = atendimentoDTO.descricaoDoAtendimento();
        this.valorDoAtendimento = atendimentoDTO.valorDoAtendimento();
        this.dataDoAtendimento = atendimentoDTO.dataDoAtendimento();
    }
}
