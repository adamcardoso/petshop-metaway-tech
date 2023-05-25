package com.metaway.petshop.dto;

import com.metaway.petshop.services.validation.UsuarioUpdateValid;

@UsuarioUpdateValid
public class UsuarioUpdateDTO extends UsuarioDTO {
    private static final long serialVersionUID = 1L;

    public String getUsername() {
        return super.getNomeDoUsuario();
    }
}
