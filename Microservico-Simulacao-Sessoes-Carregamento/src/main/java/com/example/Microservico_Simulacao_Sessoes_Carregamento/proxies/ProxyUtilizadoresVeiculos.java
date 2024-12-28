package com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "Microservico-Utilizadores-Veiculos")
public interface ProxyUtilizadoresVeiculos {

    @GetMapping("/veiculos/{id}/capacidadeCarregamento")
    public double capacidadeCarregamento(@PathVariable Long id);

    @GetMapping("/veiculos/{id}/bateriaAtual")
    public double bateriaAtual(@PathVariable Long id);

    @GetMapping("/veiculos/{id}/bateriaTotal")
    public double bateriaTotal(@PathVariable Long id);

    @PutMapping("/veiculos/{id}/bateria")
    public ResponseEntity<Double> atualizaBateria(@PathVariable Long id, @RequestParam double bateria);
}
