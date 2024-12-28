package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.SessaoCarregamento;

import java.util.Optional;

@FeignClient(value="Microservico-Simulacao-Sessoes-Carregamento")
public interface ProxySimulacaoSessaoCarregamento {

    @PostMapping("/Simulacao/{idPosto}/{idVeiculo}/{emailUtilizador}/{cemeId}")
    public Long registrar(@PathVariable Long idPosto, @PathVariable Long idVeiculo, @PathVariable String emailUtilizador, @PathVariable Long cemeId);

    @GetMapping("/Simulacao/{id}")
    public Optional<SessaoCarregamento> consultar(@PathVariable Long id);
}
