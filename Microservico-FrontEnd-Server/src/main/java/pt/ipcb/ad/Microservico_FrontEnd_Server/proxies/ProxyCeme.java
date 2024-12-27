package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.CEME;

import java.util.List;

@FeignClient(value="Microservico-CEME-Faturacao")
public interface ProxyCeme {

    @GetMapping("/CEME")
    public List<CEME> listar();

}
