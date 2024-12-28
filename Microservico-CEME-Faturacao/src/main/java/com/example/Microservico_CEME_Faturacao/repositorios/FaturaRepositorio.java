package com.example.Microservico_CEME_Faturacao.repositorios;

import com.example.Microservico_CEME_Faturacao.modelos.Fatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FaturaRepositorio extends JpaRepository<Fatura, Long> {

    List<Fatura> findByEmailUtilizadorOrderByIdDesc(String emailUtilizador);

}
