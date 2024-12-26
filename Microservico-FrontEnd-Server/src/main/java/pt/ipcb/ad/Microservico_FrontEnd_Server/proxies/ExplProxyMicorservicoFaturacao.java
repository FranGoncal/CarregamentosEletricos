package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.ExplFatura;

@FeignClient(value = "microservico-faturacao")
public interface ExplProxyMicorservicoFaturacao {

    @GetMapping("/faturas/{marca}/{modelo}/{KmP}/{KC}")
    public ExplFatura getFatura(@PathVariable String marca,
                                @PathVariable String modelo,
                                @PathVariable Double KmP,
                                @PathVariable Double KC);

}
