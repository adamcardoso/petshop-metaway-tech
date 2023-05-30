package com.metaway.petshop.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "contatoUuid") // Alterado para contatoUuid
@Entity
@Table(name = "tb_contato")
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Código de identificação do contato")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID contatoUuid; // Alterado para contatoUuid

    private String tag;

    private String email;

    private String telefone;

    private String valor;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Contato(UUID contatoUuid, String tag, String email, String telefone, String valor, Cliente cliente) {
        this.contatoUuid = contatoUuid;
        this.tag = tag;
        this.email = email;
        this.telefone = telefone;
        this.valor = valor;
        this.cliente = cliente;
    }
}
