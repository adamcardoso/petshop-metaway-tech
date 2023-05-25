package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Perfil;
import com.metaway.petshop.entities.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
    private UUID usuarioUuid;
    private String cpf;
    private String nomeDoUsuario;
    private String senha;
    private Set<Perfil> perfis;

    public UsuarioDTO(Usuario entity) {
        usuarioUuid = entity.getUsuarioUuid();
        cpf = entity.getCpf();
        nomeDoUsuario = entity.getNomeDoUsuario();
        senha = entity.getSenha();
        perfis = entity.getPerfis();
    }
}
