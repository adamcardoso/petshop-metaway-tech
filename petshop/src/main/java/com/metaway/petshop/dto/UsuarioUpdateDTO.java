package com.metaway.petshop.dto;

import com.metaway.petshop.services.validation.UsuarioUpdateValid;

@UsuarioUpdateValid
public record UsuarioUpdateDTO(String nomeDoUsuario) {
    public String getUsername() {
        return nomeDoUsuario;
    }
}