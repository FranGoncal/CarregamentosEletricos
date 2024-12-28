package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/CEME/{id}/nome")
    public String getNome(@PathVariable Long id);

    @GetMapping("/CEME")
    public List<CEME> getFornecedoresProrios(@RequestParam String ownerEmail);

    @GetMapping("/CEME/consultar")
    public CEME getCeme(@RequestParam Long id);

    @PutMapping("/CEME/editar")
    public CEME editaCeme(@RequestParam Long id,@RequestParam String name ,@RequestParam double precoPorKWh);

    @PostMapping("/CEME/criar")
    public CEME criarCeme(@RequestParam String email,@RequestParam String fornecedor,@RequestParam double precoPorKWH);


    @DeleteMapping("CEME/eliminar")
    public ResponseEntity<String> eliminarCeme(@RequestParam Long id);
}
