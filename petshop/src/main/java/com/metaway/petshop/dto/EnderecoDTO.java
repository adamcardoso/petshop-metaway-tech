package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

public record EnderecoDTO(
        @Schema(description = "Código de identificação do endereço")
        UUID enderecoUuid,
        @Schema(description = "ID do cliente associado ao endereço")
        UUID clienteUuid,

        @Schema(description = "Logradouro do endereço")
        String logradouro,

        @Schema(description = "Cidade do endereço")
        String cidade,

        @Schema(description = "Bairro do endereço")
        String bairro,

        @Schema(description = "Complemento do endereço")
        String complemento,

        @Schema(description = "Tag do endereço")
        String tag
) {
    public EnderecoDTO(Endereco endereco) {
        this(
                endereco.getEnderecoUuid(),
                endereco.getCliente().getClienteUuid(),
                endereco.getLogradouro(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getComplemento(),
                endereco.getTag()
        );
    }

    public Endereco toEntity() {
        Endereco endereco = new Endereco();
        endereco.setEnderecoUuid(this.enderecoUuid());
        endereco.setLogradouro(this.logradouro());
        endereco.setCidade(this.cidade());
        endereco.setBairro(this.bairro());
        endereco.setComplemento(this.complemento());
        endereco.setTag(this.tag());

        return endereco;
    }
}


