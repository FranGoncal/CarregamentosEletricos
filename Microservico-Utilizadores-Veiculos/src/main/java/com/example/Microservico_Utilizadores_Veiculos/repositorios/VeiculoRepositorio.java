package com.example.Microservico_Utilizadores_Veiculos.repositorios;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VeiculoRepositorio extends JpaRepository<Veiculo, Long> {

    List<Veiculo> findByUtilizadorId(Long userId);

}
