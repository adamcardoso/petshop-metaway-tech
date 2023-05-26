package com.metaway.petshop.config.security;

import com.metaway.petshop.entities.Usuario;
import com.metaway.petshop.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UsuarioRepository usuarioRepository;

    public AuthenticationServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByNomeDoUsuario(username);
        if (Objects.isNull(usuario)) {
            throw new UsernameNotFoundException("Usuário não encontrado!");
        }
        return new User(usuario.getNomeDoUsuario(), usuario.getSenha(), Collections.emptyList());
    }
}