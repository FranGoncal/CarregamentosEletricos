package com.example.Microservico_CEME_Faturacao.repositorios;

import com.example.Microservico_CEME_Faturacao.modelos.CEME;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CEMERepositorio extends JpaRepository<CEME, Long> {

    List<CEME> findByOwnerEmail(@Param("ownerEmail") String ownerEmail);
}
