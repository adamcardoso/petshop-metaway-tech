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
@EqualsAndHashCode(of = "enderecoUuid")
@Entity
@Table(name = "tb_endereco")
public class Endereco implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Código de identificação do endereço")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID enderecoUuid;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private String logradouro;

    private String cidade;

    private String bairro;

    private String complemento;

    private String tag;

    public void setClienteId(UUID clienteUuid) {
        this.cliente = new Cliente();
        this.cliente.setClienteUuid(clienteUuid);
    }
}
