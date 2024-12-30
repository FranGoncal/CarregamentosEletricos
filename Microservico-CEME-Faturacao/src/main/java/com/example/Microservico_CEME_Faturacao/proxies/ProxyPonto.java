package com.example.Microservico_CEME_Faturacao.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value="OPC-Gestao-de-Pontos")
public interface ProxyPonto {
    @GetMapping("/ponto/Taxa/{id}")
    public double getTaxa(@PathVariable Long id);

}
