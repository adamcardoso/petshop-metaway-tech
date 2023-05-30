package com.metaway.petshop.entities;

import com.metaway.petshop.entities.enums.PerfilEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class UsuarioTest {

    @Test
    public void testUsuario() {
        // Criação de objeto Usuario
        Usuario usuario = new Usuario();
        UUID usuarioUuid = UUID.randomUUID();
        usuario.setUsuarioUuid(usuarioUuid);
        usuario.setCpf("12345678901");
        usuario.setNomeDoUsuario("joao");
        usuario.setSenha("senha");

        Perfil perfil = new Perfil();
        perfil.setNomeDoPerfil(PerfilEnum.ADMIN);
        usuario.getPerfis().add(perfil);

        // Verificações dos atributos do Usuario
        Assertions.assertEquals(usuarioUuid, usuario.getUsuarioUuid());
        Assertions.assertEquals("12345678901", usuario.getCpf());
        Assertions.assertEquals("joao", usuario.getNomeDoUsuario());
        Assertions.assertEquals("senha", usuario.getSenha());
        Assertions.assertEquals(1, usuario.getPerfis().size());
        Assertions.assertTrue(usuario.getPerfis().contains(perfil));
    }

    @Test
    public void testAuthorities() {
        Usuario usuario = new Usuario();
        Perfil perfil = new Perfil();
        perfil.setNomeDoPerfil(PerfilEnum.CLIENTE);
        usuario.getPerfis().add(perfil);

        Assertions.assertEquals(1, ((UserDetails) usuario).getAuthorities().size());
        Assertions.assertEquals(new SimpleGrantedAuthority("CLIENTE"), ((UserDetails) usuario).getAuthorities().iterator().next());
    }

    @Test
    public void testAccountNonExpired() {
        Usuario usuario = new Usuario();
        Assertions.assertTrue(usuario.isAccountNonExpired());
    }

    @Test
    public void testAccountNonLocked() {
        Usuario usuario = new Usuario();
        Assertions.assertTrue(usuario.isAccountNonLocked());
    }

    @Test
    public void testCredentialsNonExpired() {
        Usuario usuario = new Usuario();
        Assertions.assertTrue(usuario.isCredentialsNonExpired());
    }

    @Test
    public void testEnabled() {
        Usuario usuario = new Usuario();
        Assertions.assertTrue(usuario.isEnabled());
    }

    @Test
    public void testEqualsAndHashCode() {
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        UUID usuarioUuid = UUID.randomUUID();
        usuario1.setUsuarioUuid(usuarioUuid);
        usuario2.setUsuarioUuid(usuarioUuid);

        Assertions.assertEquals(usuario1, usuario2);
        Assertions.assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }
}
