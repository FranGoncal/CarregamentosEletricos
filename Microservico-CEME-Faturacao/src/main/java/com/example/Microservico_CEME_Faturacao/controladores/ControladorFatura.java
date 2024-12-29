package com.example.Microservico_CEME_Faturacao.controladores;


import com.example.Microservico_CEME_Faturacao.modelos.CEME;
import com.example.Microservico_CEME_Faturacao.modelos.Fatura;
import com.example.Microservico_CEME_Faturacao.modelos.SessionDetails;
import com.example.Microservico_CEME_Faturacao.repositorios.CEMERepositorio;
import com.example.Microservico_CEME_Faturacao.repositorios.FaturaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ControladorFatura {

    @Autowired
    FaturaRepositorio faturaRepositorio;

    @Autowired
    CEMERepositorio cemeRepositorio;

    @PostMapping("/CEME-faturacao")
    public Fatura criar(@RequestBody SessionDetails sessionDetails) {
        Fatura fatura = new Fatura();

        fatura.setIdCeme(sessionDetails.getIdCeme());
        fatura.setDataEmitida(LocalDateTime.now());
        fatura.setSessaoId(sessionDetails.getSessionId());
        fatura.setEmailUtilizador(sessionDetails.getUserEmail());
        fatura.setConsumoEnergia(sessionDetails.getEnergyConsumed());
        fatura.setVeiculoId(sessionDetails.getVeiculoId());

        double energiaConsumida = sessionDetails.getEnergyConsumed();
        System.out.println(energiaConsumida);
        CEME ceme = cemeRepositorio.findById(sessionDetails.getIdCeme()).get();
        double precoPorKWh = ceme.getPrecoPorKWh();

        //TODO atualizar o resto da formula das partes que nao entendemos bem
        double custoTotal = precoPorKWh * energiaConsumida;

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
    @GetMapping("/faturas/{emailUtilizador}")
    public List<Fatura> consultarByEmail(@RequestParam String emailUtilizador) {
        return faturaRepositorio.findByEmailUtilizadorOrderByIdDesc(emailUtilizador);
    }
}
