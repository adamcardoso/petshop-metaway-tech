package com.metaway.petshop.dto;

import com.metaway.petshop.services.validation.UsuarioInsertValid;

@UsuarioInsertValid
public class UsuarioInsertDTO extends UsuarioDTO {
    private static final long serialVersionUID = 1L;

    private String password;

    UsuarioInsertDTO() {
        super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return super.getNomeDoUsuario();
    }
}
