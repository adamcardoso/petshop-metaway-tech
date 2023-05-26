package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Endereco;

import java.util.UUID;

public record EnderecoDTO(
        UUID enderecoUuid,
        UUID clienteUuid,
        String logradouro,
        String cidade,
        String bairro,
        String complemento,
        String tag
) {
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
