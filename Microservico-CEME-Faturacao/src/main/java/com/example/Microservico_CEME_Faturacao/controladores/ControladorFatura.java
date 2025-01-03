package com.example.Microservico_CEME_Faturacao.controladores;


import com.example.Microservico_CEME_Faturacao.modelos.CEME;
import com.example.Microservico_CEME_Faturacao.modelos.Fatura;
import com.example.Microservico_CEME_Faturacao.modelos.SessionDetails;
import com.example.Microservico_CEME_Faturacao.proxies.ProxyPonto;
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

    @Autowired
    ProxyPonto proxyPonto;

    private final double TAR_FIXA = 0.95;
    private final double IVA = 1.23;

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
        CEME ceme = cemeRepositorio.findById(sessionDetails.getIdCeme()).get();
        double precoPorKWh = ceme.getPrecoPorKWh();

        Long idPonto = sessionDetails.getPostoId();
        double taxaPonto = proxyPonto.getTaxa(idPonto);

        double taxaCEME = cemeRepositorio.findById(sessionDetails.getIdCeme()).get().getTaxaCEME();

        double custoTotal = precoPorKWh * energiaConsumida;//custo do preco da energia
        custoTotal= ((Math.ceil(custoTotal * 100) / 100) + taxaPonto + taxaCEME + TAR_FIXA ) * IVA;

        custoTotal = (Math.ceil(custoTotal * 100) / 100);

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
