package com.metaway.petshop.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ClienteDTO {

    @Schema(description = "Código de identificação do cliente")
    private UUID clienteUuid;

    @Schema(description = "Nome do cliente")
    private String nomeDoCliente;

    @Schema(description = "CPF do cliente")
    private String cpf;

    @Schema(description = "Data de cadastro do cliente")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataDeCadastro;

    private List<EnderecoDTO> enderecos;

    private List<PetsDTO> pets;
}
