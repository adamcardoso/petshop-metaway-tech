package com.metaway.petshop.component;

import com.metaway.petshop.config.security.TokenService;
import com.metaway.petshop.entities.Usuario;
import com.metaway.petshop.repositories.UsuarioRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;
    private final UsuarioRepository usuarioRepository;

    public TokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = "";
        String authorizationHeader = request.getHeader("Authorization");
        if (!Objects.isNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            if (tokenService.validarToken(token)) {
                String subject = tokenService.getSubject(token);
                Usuario usuario = usuarioRepository.findByNomeDoUsuario(subject);
                if (!Objects.isNull(usuario)) {
                    var authentication = new UsernamePasswordAuthenticationToken(
                            usuario, null, usuario.getPerfis());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            if (request.getRequestURI().equals("/api/logout")) {
                tokenService.invalidarToken(token);
                request.getSession().invalidate();
            }
        }
    }
}
