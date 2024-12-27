package com.example.Microservico_CEME_Faturacao.repositorios;

import com.example.Microservico_CEME_Faturacao.modelos.CEME;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CEMERepositorio extends JpaRepository<CEME, Long> {

}
