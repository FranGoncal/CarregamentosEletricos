package com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies;


import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.Fatura;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.SessionDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "Microservico-CEME-Faturacao")
public interface ProxyCemeFaturacao {

    @PostMapping("/CEME-faturacao")
    public Fatura criar(@RequestBody SessionDetails sessionDetails);

}