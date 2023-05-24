package com.metaway.petshop.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "uuid")
@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Código de identificação do endereço")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String logradouro;

    private String cidade;

    private String bairro;

    private String complemento;

    private String tag;
}
