package com.example.Microservico_Utilizadores_Veiculos.repositorios;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UtilizadorRepositorio extends JpaRepository<Utilizador, Long> {

    Optional<Utilizador> findById(Long id);

    Optional<Utilizador> findByEmail(String email);

    @Query("SELECT u.email FROM Utilizador u")
    List<String> findAllEmails();
}
