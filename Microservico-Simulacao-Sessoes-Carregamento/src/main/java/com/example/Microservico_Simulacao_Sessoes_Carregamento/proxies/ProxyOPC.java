package com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "OPC-Gestao-de-Pontos")
public interface ProxyOPC {

    @PutMapping("/pontos-carregamento/{id}")
    public Integer atualizar(@PathVariable Long id, @RequestParam String status);

    @GetMapping("/pontos-carregamento/estado/{id}")
    public String consultarEstado(@PathVariable Long id);

}
