package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Utilizador;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.UtilizadorDTO;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Veiculo;

import java.util.Set;

@FeignClient(value="Microservico-Utilizadores-Veiculos")
public interface ProxyMicroservicoUtilizadorVeiculo {

    @PostMapping("/utilizadores")
    public ResponseEntity<String> registrar(@RequestParam String email, @RequestParam String nome, @RequestParam String password, @RequestParam String role);

    @GetMapping("/utilizadores/autenticacao")
    public UtilizadorDTO autenticacao(@RequestParam String email, @RequestParam String password);

    @GetMapping("/utilizadores/veiculos")
    public Set<Veiculo> consultarVeiculos(@RequestParam String email);

}
