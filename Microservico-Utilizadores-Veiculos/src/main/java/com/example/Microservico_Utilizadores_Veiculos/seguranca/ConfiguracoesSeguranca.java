package com.example.Microservico_Utilizadores_Veiculos.seguranca;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfiguracoesSeguranca {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Desabilita completamente a segurança
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/**").permitAll()
                    .anyRequest().permitAll())
                .formLogin(form -> form.disable())
                .httpBasic(httpb->httpb.disable())

                .build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
