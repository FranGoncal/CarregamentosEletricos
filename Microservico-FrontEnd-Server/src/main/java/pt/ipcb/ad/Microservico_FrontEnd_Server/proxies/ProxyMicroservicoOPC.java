package pt.ipcb.ad.Microservico_FrontEnd_Server.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.PontoCarregamento;
import pt.ipcb.ad.Microservico_FrontEnd_Server.modelos.PontoCarregamentoDTO;

import java.util.List;
import java.util.Optional;

@FeignClient(value="OPC-Gestao-de-Pontos")
public interface ProxyMicroservicoOPC {

    @GetMapping("/pontos-carregamento")
    public List<PontoCarregamentoDTO> listar(@RequestParam String local);

    @GetMapping("/pontos-carregamento/{id}")
    public PontoCarregamentoDTO consultar(@PathVariable Long id);

    @GetMapping("/pontos-carregamento/estado/{id}")
    public String consultarEstado(@PathVariable Long id);

    @GetMapping("/opc")
    public List<PontoCarregamento> getPontosProrios(@RequestParam String ownerEmail);

    @GetMapping("/opc/ponto")
    public Optional<PontoCarregamento> getPontoProrio(@RequestParam Long id);

    @PutMapping("/opc/ponto/editar")
    public PontoCarregamento editaPonto(@RequestParam Long id, @RequestParam String local, @RequestParam String estado, @RequestParam double capacidade);

    @PostMapping("/opc/ponto/criar")
    public PontoCarregamento criarPonto(@RequestParam String email, @RequestParam String local, @RequestParam String estado, @RequestParam double capacidade);


    @DeleteMapping("/opc/ponto/eliminar")
    public ResponseEntity<String> eliminarPonto(@RequestParam Long id);
}
