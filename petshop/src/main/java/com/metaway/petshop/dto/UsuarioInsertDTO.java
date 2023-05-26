package com.metaway.petshop.dto;

import com.metaway.petshop.entities.Perfil;
import com.metaway.petshop.services.validation.UsuarioInsertValid;

import java.util.Set;
import java.util.UUID;

@UsuarioInsertValid
public record UsuarioInsertDTO(String senha, UUID usuarioUuid, String cpf, String nomeDoUsuario, Set<Perfil> perfis) {
    public String getUsername() {
        return nomeDoUsuario;
    }
}