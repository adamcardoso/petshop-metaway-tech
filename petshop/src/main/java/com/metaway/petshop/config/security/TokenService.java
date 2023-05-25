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
public class TokenService {

    /**
     * Secret utilizado na geração de tokens JWT.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Set contendo os tokens JWT válidos.
     */
    private final Set<String> tokensValidos = new HashSet<>();

    /**
     * Gera um token JWT para o usuário informado.
     *
     * @param usuario usuário para o qual o token será gerado.
     * @return o token JWT gerado.
     */
    public String gerarToken(Usuario usuario) {
        String token = JWT.create()
                .withIssuer("Processos")
                .withSubject(usuario.getUsername())
                .withClaim("id", Collections.singletonList(usuario.getUsuarioUuid()))
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusMinutes(3)
                        .toInstant(ZoneOffset.of("-03:00")))
                ).sign(Algorithm.HMAC256(secret));

        tokensValidos.add(token);

        return token;
    }

    /**
     * Obtém o subject (username) a partir do token JWT informado.
     *
     * @param token token JWT do qual o subject será obtido.
     * @return o subject (username) contido no token JWT.
     */
    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("Processos")
                .build().verify(token).getSubject();
    }

    /**
     * Verifica se o token JWT informado é válido.
     *
     * @param token token JWT a ser verificado.
     * @return true se o token JWT for válido, false caso contrário.
     */
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

    /**
     * Invalida o token JWT informado.
     *
     * @param token token JWT a ser invalidado.
     */
    public void invalidarToken(String token) {
        tokensValidos.remove(token);
    }
}

