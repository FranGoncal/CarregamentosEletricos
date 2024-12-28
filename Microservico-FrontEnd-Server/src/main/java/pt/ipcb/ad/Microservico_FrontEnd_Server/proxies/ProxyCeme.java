package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.CEME;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Fatura;

import java.util.List;
import java.util.Optional;

@FeignClient(value="Microservico-CEME-Faturacao")
public interface ProxyCeme {

    @GetMapping("/CEME")
    public List<CEME> listar();

    @GetMapping("/faturas/{emailUtilizador}")
    public List<Fatura> consultarByEmailOrderByIdDesc(@RequestParam String emailUtilizador);

    @GetMapping("/faturacao/{id}")
    public Optional<Fatura> consultar(@PathVariable Long id);
}
