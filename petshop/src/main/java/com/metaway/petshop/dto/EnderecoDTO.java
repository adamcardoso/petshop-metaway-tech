package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Cliente;
import com.metaway.petshop.entities.Endereco;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Objects;
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
    public static EnderecoDTO fromEntity(Endereco endereco) {
        Cliente cliente = endereco.getCliente();
        if (Objects.isNull(cliente)) {
            throw new IllegalStateException("Endereco has no associated Cliente");
        }
        return new EnderecoDTO(
                endereco.getEnderecoUuid(),
                cliente.getClienteUuid(),
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
        endereco.setClienteId(this.clienteUuid());
        endereco.setLogradouro(this.logradouro());
        endereco.setCidade(this.cidade());
        endereco.setBairro(this.bairro());
        endereco.setComplemento(this.complemento());
        endereco.setTag(this.tag());

        return endereco;
    }
}
