package com.metaway.petshop.config.security;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.web.SecurityFilterChain;

public interface TokenFilterConfig {
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception;
    Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer();
}
