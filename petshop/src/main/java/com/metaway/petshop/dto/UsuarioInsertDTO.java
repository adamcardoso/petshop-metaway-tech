package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Perfil;
import com.metaway.petshop.services.validation.UsuarioInsertValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;
import java.util.UUID;

@UsuarioInsertValid
public record UsuarioInsertDTO(
        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
        String senha,

        @NotNull(message = "O UUID do usuário é obrigatório")
        UUID usuarioUuid,

        @NotBlank(message = "O CPF é obrigatório")
        String cpf,

        @NotBlank(message = "O nome do usuário é obrigatório")
        String nomeDoUsuario,

        @NotNull(message = "Os perfis são obrigatórios")
        Set<Perfil> perfis,

        UsuarioDTO userDTO
) {
    public String getUsername() {
        return nomeDoUsuario;
    }

    // Getters para os outros atributos
    public String getSenha() {
        return senha;
    }

    public UUID getUsuarioUuid() {
        return usuarioUuid;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNomeDoUsuario() {
        return nomeDoUsuario;
    }

    public Set<Perfil> getPerfis() {
        return perfis;
    }

    public UsuarioInsertDTO{
        userDTO = new UsuarioDTO();
    }
}