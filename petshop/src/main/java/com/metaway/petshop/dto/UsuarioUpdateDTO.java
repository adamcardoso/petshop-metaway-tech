package com.metaway.petshop.dto;

import com.metaway.petshop.services.validation.UsuarioUpdateValid;

@UsuarioUpdateValid
public record UsuarioUpdateDTO(String nomeDoUsuario, UsuarioDTO userDTO) {
    public String getUsername() {
        return nomeDoUsuario;
    }

    public UsuarioUpdateDTO{
        userDTO = new UsuarioDTO();
    }
}