package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Perfil;
import com.metaway.petshop.entities.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;
import java.util.UUID;

public record UsuarioDTO(
        @Schema(description = "Código de identificação do usuário")
        UUID usuarioUuid,
        @Schema(description = "CPF do usuário")
        String cpf,
        @Schema(description = "Nome do usuário")
        String nomeDoUsuario,
        @Schema(description = "Senha do usuário")
        String senha,
        @Schema(description = "Conjunto de perfis do usuário")
        Set<Perfil> perfis
) {
    public UsuarioDTO(Usuario entity) {
        this(
                entity.getUsuarioUuid(),
                entity.getCpf(),
                entity.getNomeDoUsuario(),
                entity.getSenha(),
                entity.getPerfis()
        );
    }

    public UsuarioDTO() {
        this(UUID.randomUUID(), "", "", "", Set.of());
    }
}
