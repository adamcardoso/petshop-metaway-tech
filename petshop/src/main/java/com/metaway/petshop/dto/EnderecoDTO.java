package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Endereco;
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
    private UUID clienteUuid;
    private String logradouro;
    private String cidade;
    private String bairro;
    private String complemento;
    private String tag;

    public Endereco toEntity() {
        Endereco entity = new Endereco();
        entity.setEnderecoUuid(enderecoUuid);
        entity.setClienteId(clienteUuid);
        entity.setLogradouro(logradouro);
        entity.setCidade(cidade);
        entity.setBairro(bairro);
        entity.setComplemento(complemento);
        entity.setTag(tag);
        return entity;
    }
}
