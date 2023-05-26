package com.metaway.petshop.config.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.metaway.petshop.entities.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenServiceImpl implements TokenService {

    @Value("${jwt.secret}")
    private String secret;

    private final Set<String> tokensValidos = new HashSet<>();

    @Override
    public String gerarToken(Usuario usuario) {
        String token = JWT.create()
                .withIssuer("Processos")
                .withSubject(usuario.getUsername())
                .withClaim("id", Collections.singletonList(usuario.getUsuarioUuid()))
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(3)
                        .toInstant(ZoneOffset.of("-03:00"))))
                .sign(Algorithm.HMAC256(secret));

        tokensValidos.add(token);

        return token;
    }

    @Override
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Processos")
                .build().verify(token).getSubject();
    }

    @Override
    public boolean validarToken(String token) {
        if (tokensValidos.contains(token)) {
            return true;
        }

        try {
            JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("Processos")
                    .build().verify(token);
            tokensValidos.add(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    @Override
    public void invalidarToken(String token) {
        tokensValidos.remove(token);
    }
}
