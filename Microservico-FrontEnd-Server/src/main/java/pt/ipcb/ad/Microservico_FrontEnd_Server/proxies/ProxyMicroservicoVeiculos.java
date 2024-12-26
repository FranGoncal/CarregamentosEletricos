package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Veiculo;


@FeignClient(value="microservico-ev-database-server")
public interface ProxyMicroservicoVeiculos {

    @GetMapping("/veiculos/{marca}/{modelo}")
    public Veiculo getVeiculo(@PathVariable String marca,
                              @PathVariable String modelo);

    @GetMapping("/veiculos/autonomia/{marca}/{modelo}")
    Integer getAutonomia(@PathVariable String marca,
                         @PathVariable String modelo);
}
