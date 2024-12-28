package com.example.OPC.repositorios;

import com.example.OPC.modelos.PontoCarregamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PontoCarregamentoRepositorio extends JpaRepository<PontoCarregamento, Long> {

    // Consultar pontos de carregamento pelo local
    List<PontoCarregamento> findByLocalContainingIgnoreCase(@Param("local") String local);

    // Consultar ponto de carregamento pelo ID
    @Query("SELECT p FROM PontoCarregamento p WHERE p.id = :id")
    Optional<PontoCarregamento> findById(@Param("id") Long id);

    // Atualizar o estado de operacionalidade de um ponto pelo ID
    @Transactional
    @Modifying
    @Query("UPDATE PontoCarregamento p SET p.estado = :status WHERE p.id = :id")
    Integer updateStatusById(@Param("id") Long id, @Param("status") String status);

    List<PontoCarregamento> findByOwnerEmail(@Param("ownerEmail") String ownerEmail);
}
