package com.metaway.petshop.api.impl;

import com.metaway.petshop.api.interfaces.AuthController;
import com.metaway.petshop.config.security.TokenServiceImpl;
import com.metaway.petshop.dto.Login;
import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.entities.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class AuthControllerImpl implements AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthControllerImpl.class);
    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
    public static final String HOME_PAGE_URI = "/home";

    private final AuthenticationManager authenticationManager;
    private final TokenServiceImpl tokenServiceImpl;

    public AuthControllerImpl(AuthenticationManager authenticationManager, TokenServiceImpl tokenServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.tokenServiceImpl = tokenServiceImpl;
    }

    @Override
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody Login login) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(login.usuario(), login.senha());

            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            if (authentication.isAuthenticated()) {
                Usuario usuario = (Usuario) authentication.getPrincipal();

                logger.info("Login realizado com sucesso para o usuário {}", usuario.getNomeDoUsuario());
                String token = tokenServiceImpl.gerarToken(usuario);

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_PREFIX + token);

                if (tokenServiceImpl.validarToken(token)) {
                    return ResponseEntity.ok()
                            .headers(headers)
                            .location(URI.create(HOME_PAGE_URI))
                            .body(new UsuarioDTO(usuario));
                }
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (AuthenticationException e) {
            logger.error("Erro de autenticação: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @Override
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        try {
            tokenServiceImpl.invalidarToken(token);
            // Redirecionar para a página de login
            //response.sendRedirect(request.getContextPath() + "/login");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao fazer logout", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
