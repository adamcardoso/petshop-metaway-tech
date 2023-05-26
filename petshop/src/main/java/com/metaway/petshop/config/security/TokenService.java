package com.metaway.petshop.config.security;

import com.metaway.petshop.entities.Usuario;

public interface TokenService {
    String gerarToken(Usuario usuario);
    String getSubject(String token);
    boolean validarToken(String token);
    void invalidarToken(String token);
}