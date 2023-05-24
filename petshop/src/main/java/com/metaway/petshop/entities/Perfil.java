package com.metaway.petshop.entities;

import com.metaway.petshop.entities.enums.PerfilEnum;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_perfil")
@EqualsAndHashCode(of = "uuid")
public class Perfil implements GrantedAuthority, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID uuid;

    @Enumerated(EnumType.STRING)
    private PerfilEnum nomeDoPerfil;

    @Override
    public String getAuthority() {
        return String.valueOf(nomeDoPerfil);
    }
}
