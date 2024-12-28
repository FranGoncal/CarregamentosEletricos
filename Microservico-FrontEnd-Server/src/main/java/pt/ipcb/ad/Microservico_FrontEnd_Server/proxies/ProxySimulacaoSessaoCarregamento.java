package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.SessaoCarregamento;

import java.util.List;
import java.util.Optional;

@FeignClient(value="Microservico-Simulacao-Sessoes-Carregamento")
public interface ProxySimulacaoSessaoCarregamento {

    @PostMapping("/Simulacao/{idPosto}/{idVeiculo}/{emailUtilizador}/{cemeId}")
    public Long registrar(@PathVariable Long idPosto, @PathVariable Long idVeiculo, @PathVariable String emailUtilizador, @PathVariable Long cemeId);

    @GetMapping("/Simulacao/{id}")
    public Optional<SessaoCarregamento> consultar(@PathVariable Long id);

    @GetMapping("/Simulacao/{id}/percentagemCarregamento")
    public int getPercentagemCarregamento(@PathVariable Long id);

    @PutMapping("/Simulacao/terminar/{id}")
    public ResponseEntity<String> atualizar(@PathVariable Long id);

    @GetMapping("/Simulacao/utilizador/{emailUtilizador}")
    public List<SessaoCarregamento> getSimulacoesByIdUtilizadorOrderByIdDesc(@PathVariable String emailUtilizador);

}
