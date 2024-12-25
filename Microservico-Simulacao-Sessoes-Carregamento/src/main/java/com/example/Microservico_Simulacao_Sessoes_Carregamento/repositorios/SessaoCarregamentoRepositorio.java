package com.example.Microservico_Simulacao_Sessoes_Carregamento.repositorios;

import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.SessaoCarregamento;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SessaoCarregamentoRepositorio extends JpaRepository<SessaoCarregamento, Long> {


    @Transactional
    @Modifying
    @Query("UPDATE SessaoCarregamento s SET s.terminada = :status WHERE s.id = :id")
    Integer updateStatusById(@Param("id") Long id, @Param("status") Boolean status);


}
