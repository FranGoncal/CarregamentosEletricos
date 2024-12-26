package com.example.Microservico_CEME_Faturacao.controladores;


import com.example.Microservico_CEME_Faturacao.modelos.Fatura;
import com.example.Microservico_CEME_Faturacao.modelos.Utilizador;
import com.example.Microservico_CEME_Faturacao.repositorios.FaturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@RestController
public class ControladorFatura {

    @Autowired
    FaturaRepositorio faturaRepositorio;



    @PostMapping("/faturacao")
    public Fatura registrar(@RequestParam Duration duracao, @RequestParam Long idUtilizador, @RequestParam Long idVeiculo, @RequestParam Long consumoEnergia) {
        Fatura fatura = new Fatura();

//        Utilizador utilizador = new Utilizador();
//        fatura.setUtilizador();

        fatura.setUtilizadorId(idUtilizador);
        fatura.setConsumoEnergia(consumoEnergia);
        fatura.setVeiculoId(idVeiculo);

        //TODO CALCULAR CUSTO TOTAL
        double custoTotal = 3;
        fatura.setCustoTotal(custoTotal);

        faturaRepositorio.save(fatura);
        return fatura;
    }

    @GetMapping("/faturacao/{id}")
    public Optional<Fatura> consultar(@PathVariable Long id) {
        return faturaRepositorio.findById(id);
    }
    @GetMapping("/faturacao")
    public List<Fatura> listar() {
        return faturaRepositorio.findAll();
    }

}
