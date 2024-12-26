package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.ExplVeiculo;


@FeignClient(value="microservico-ev-database-server")
public interface ExplProxyMicroservicoVeiculos {

    @GetMapping("/veiculos/{marca}/{modelo}")
    public ExplVeiculo getVeiculo(@PathVariable String marca,
                                  @PathVariable String modelo);

    @GetMapping("/veiculos/autonomia/{marca}/{modelo}")
    Integer getAutonomia(@PathVariable String marca,
                         @PathVariable String modelo);
}
