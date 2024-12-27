package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.PontoCarregamentoDTO;

import java.util.List;
import java.util.Optional;

@FeignClient(value="OPC-Gestao-de-Pontos")
public interface ProxyMicroservicoOPC {

    @GetMapping("/pontos-carregamento")
    public List<PontoCarregamentoDTO> listar(@RequestParam String local);

    @GetMapping("/pontos-carregamento/{id}")
    public PontoCarregamentoDTO consultar(@PathVariable Long id);
}
