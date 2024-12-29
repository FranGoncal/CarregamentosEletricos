package pt.ipcb.ad.Microservico_FrontEnd_Server.seguranca;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
//@EnableWebSecurity
public class SecurityConfig  {



    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .csrf(csrf->csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/registo","/registar","/login","/acesso-negado").permitAll()
                        .requestMatchers("/gestao/ADMIN/**").hasRole("ADMIN")  // Apenas ROLE_ADMIN pode acessar /gestao/ADMIN/**
                        .requestMatchers("/gestao/OPC/**").hasRole("OPC")  // Apenas ROLE_OPC pode acessar /gestao/OPC/**
                        .requestMatchers("/gestao/CEME/**").hasRole("CEME")
                        .anyRequest().authenticated()
                )
                .formLogin(form->form
                        .loginPage("/")
                        .failureHandler(((request, response, exception) -> {
                            request.getSession().setAttribute("error","Invalid username or password");
                            response.sendRedirect("/");
                            System.out.println("ola");
                        }))
                        .failureUrl("/?error=true")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .exceptionHandling((accessonegado)->accessonegado
                        .accessDeniedPage("/acesso-negado"))
                .build();
    }

}