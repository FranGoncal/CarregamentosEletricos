package com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "Microservico-Utilizadores-Veiculos")
public interface ProxyUtilizadoresVeiculos {

    @GetMapping("/veiculos/{id}/capacidadeCarregamento")
    public double capacidadeCarregamento(@PathVariable Long id);
}
