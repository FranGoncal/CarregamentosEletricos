package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Utilizador;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.UtilizadorDTO;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.Veiculo;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@FeignClient(value="Microservico-Utilizadores-Veiculos")
public interface ProxyMicroservicoUtilizadorVeiculo {

    @PostMapping("/utilizadores")
    public ResponseEntity<String> registrar(@RequestParam String email, @RequestParam String nome, @RequestParam String password, @RequestParam String role);

    @GetMapping("/utilizadores/autenticacao")
    public UtilizadorDTO autenticacao(@RequestParam String email, @RequestParam String password);

    @GetMapping("/utilizadores/veiculos")
    public Set<Veiculo> consultarVeiculos(@RequestParam String email);

    @GetMapping("/utilizadores/id/")
    public Long getId(@RequestParam String email);

    @GetMapping("/veiculos/{id}/nome")
    public String getNome(@PathVariable Long id);

    @GetMapping("/utilizadores/seguro")
    public List<UtilizadorDTO> listarSeguro();

    @GetMapping("/utilizador/seguro")
    public UtilizadorDTO getUserSeguro(@RequestParam Long id);

    @PutMapping("/utilizador/edicao")
    public ResponseEntity<String> editaUser(@RequestParam Long id, @RequestParam String nome, @RequestParam String role);

    @DeleteMapping("/utilizador/eliminar/{id}")
    public ResponseEntity<String> eliminarUser(@PathVariable Long id);

    @GetMapping("/veiculos/{id}")
    public Optional<Veiculo> consultar(@PathVariable Long id);

    @PutMapping("/utilizador/{id}/remover-veiculo/{idVeiculo}")
    public ResponseEntity<String> removerVeiculo(@RequestParam Long id, @RequestParam Long idVeiculo);

    @DeleteMapping("/veiculos/{id}/apagar")
    public ResponseEntity<String> apagarVeiculo(@PathVariable Long id);

    @GetMapping("/utilizadores/emails")
    public List<String> consultarEmails();

    @PutMapping("/veiculos")
    public Veiculo registrar(@RequestParam String marca, @RequestParam String modelo, @RequestParam double bateria, @RequestParam double bateriaAtual, @RequestParam double capacidadeCarregamento, @RequestParam Long id);

    @PutMapping("/utilizador/{email}/adiciona-veiculo")
    public ResponseEntity<String> adicionaVeiculo(@PathVariable String email, @RequestBody Veiculo veiculo);

    @PutMapping("/veiculos/{idVeiculo}/editar")
    public ResponseEntity<String> editaVeiculo(@PathVariable Long idVeiculo,
                                               @RequestParam String marca,
                                               @RequestParam String modelo,
                                               @RequestParam double bateria,
                                               @RequestParam double bateriaAtual,
                                               @RequestParam double capacidadeCarregamento);

    @GetMapping("/utilizadores/idUser")
    public Long getUserId(@RequestParam String email);


}
