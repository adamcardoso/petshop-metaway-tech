package com.metaway.petshop.api;

import com.metaway.petshop.config.security.TokenService;
import com.metaway.petshop.dto.Login;
import com.metaway.petshop.dto.UsuarioDTO;
import com.metaway.petshop.entities.Usuario;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping(value = "/api")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    public static final String AUTHORIZATION_HEADER_PREFIX = "Bearer ";
    public static final String HOME_PAGE_URI = "/home";
    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Operation(description = "Faz login")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Retorno OK para login"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "403", description = "Erro de autorização dessa API"),
            @ApiResponse(responseCode = "404", description = "Recurso não encontrado")})
    @Parameters(value = {@Parameter(name = "id", in = ParameterIn.PATH)})
    @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@Valid @RequestBody Login login) {
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(login.username(),
                            login.password());

            Authentication authenticate = this.authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);

            var usuario = (Usuario) authenticate.getPrincipal();

            logger.info("Login realizado com sucesso para o usuário {}", usuario.getNomeDoUsuario());
            String token = tokenService.gerarToken(usuario);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.AUTHORIZATION, AUTHORIZATION_HEADER_PREFIX + token);

            if (tokenService.validarToken(token)) {
                return ResponseEntity.ok()
                        .headers(headers)
                        .location(URI.create(HOME_PAGE_URI))
                        .body(new UsuarioDTO(usuario));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (AuthenticationException e) {
            logger.error("Erro de autenticação", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @Operation(description = "Faz logout")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Retorno OK para logout"),
            @ApiResponse(responseCode = "401", description = "Erro de autenticação dessa API"),
            @ApiResponse(responseCode = "500", description = "Erro inesperado")})
    @PostMapping(value = "/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        try {
            tokenService.invalidarToken(token);
            // Redirecionar para a página de login
            //response.sendRedirect(request.getContextPath() + "/login");
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Erro ao fazer logout", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
