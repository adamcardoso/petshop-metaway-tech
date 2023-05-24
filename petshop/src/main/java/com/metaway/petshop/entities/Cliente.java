package com.metaway.petshop.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "clienteUuid") // Alterado para clienteUuid
@Entity
@Table(name = "tb_cliente")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "Código de identificação do cliente")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID clienteUuid; // Alterado para clienteUuid

    @Schema(description = "Nome do cliente")
    private String nomeDoCliente;

    @Schema(description = "CPF do cliente")
    private String cpf;

    @Schema(description = "Data de cadastro do cliente")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDeCadastro;

    @OneToMany(mappedBy = "cliente")
    private List<Endereco> enderecos;

    @OneToMany(mappedBy = "cliente")
    private List<Pets> pets;
}
