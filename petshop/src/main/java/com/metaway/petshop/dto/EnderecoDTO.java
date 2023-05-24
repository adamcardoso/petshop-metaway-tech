package com.metaway.petshop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnderecoDTO {
    private UUID enderecoUuid;
    private UUID clienteId;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String tag;
}
