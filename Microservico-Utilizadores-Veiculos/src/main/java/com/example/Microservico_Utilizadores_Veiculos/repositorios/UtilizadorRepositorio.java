package com.example.Microservico_Utilizadores_Veiculos.repositorios;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UtilizadorRepositorio extends JpaRepository<Utilizador, Long> {

    Optional<Utilizador> findById(Long id);

    Optional<Utilizador> findByEmail(String email);

}
