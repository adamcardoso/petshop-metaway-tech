package com.metaway.petshop.dto;

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
    private Set<String> perfis;
}
