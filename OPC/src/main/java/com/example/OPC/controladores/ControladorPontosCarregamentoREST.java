package com.example.OPC.controladores;

import com.example.OPC.modelos.PontoCarregamento;
import com.example.OPC.modelos.PontoCarregamentoDTO;
import com.example.OPC.repositorios.PontoCarregamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ControladorPontosCarregamentoREST {

    @Autowired
    PontoCarregamentoRepositorio pontoCarregamentoRepositorio;

    @PostMapping("/pontos-carregamento")
    public PontoCarregamento registrar(@RequestParam String local, @RequestParam double maxCapacidade) {
        PontoCarregamento pontoCarregamento = new PontoCarregamento();
        pontoCarregamento.setLocal(local);
        pontoCarregamento.setMaxCapacity(maxCapacidade);
        pontoCarregamento.setStatus("DISPONIVEL");
        pontoCarregamentoRepositorio.save(pontoCarregamento);
        return pontoCarregamento;
    }
    @GetMapping("/pontos-carregamento/{id}")
    public PontoCarregamentoDTO consultar(@PathVariable Long id) {
        Optional<PontoCarregamento> ponto = pontoCarregamentoRepositorio.findById(id);

        PontoCarregamentoDTO pontoCarregamentoDTO = new PontoCarregamentoDTO();
        pontoCarregamentoDTO.setEstado(ponto.get().getEstado());
        pontoCarregamentoDTO.setTaxaOPC(ponto.get().getTaxaOPC());
        pontoCarregamentoDTO.setMaxCapacity(ponto.get().getMaxCapacity());
        pontoCarregamentoDTO.setLocal(ponto.get().getLocal());
        pontoCarregamentoDTO.setId(ponto.get().getId());

        return pontoCarregamentoDTO;
    }

    @GetMapping("/pontos-carregamento/estado/{id}")
    public String consultarEstado(@PathVariable Long id) {
        return pontoCarregamentoRepositorio.findById(id).get().getStatus();
    }

    @GetMapping("/pontos-carregamento/capacidade/{id}")
    public double consultarCapacidade(@PathVariable Long id) {
        return pontoCarregamentoRepositorio.findById(id).get().getMaxCapacity();
    }

    @GetMapping("/pontos-carregamento")
    public List<PontoCarregamentoDTO> listar(@RequestParam String local) {

        List<PontoCarregamento> pontosCarregamentos = pontoCarregamentoRepositorio.findByLocalContainingIgnoreCase(local);
        List<PontoCarregamentoDTO> pontosCarregamentosDTO = new ArrayList<>();

        for(PontoCarregamento ponto : pontosCarregamentos) {
            PontoCarregamentoDTO pontoCarregamentoDTO = new PontoCarregamentoDTO();
            pontoCarregamentoDTO.setEstado(ponto.getEstado());
            pontoCarregamentoDTO.setTaxaOPC(ponto.getTaxaOPC());
            pontoCarregamentoDTO.setMaxCapacity(ponto.getMaxCapacity());
            pontoCarregamentoDTO.setLocal(ponto.getLocal());
            pontoCarregamentoDTO.setId(ponto.getId());
            pontosCarregamentosDTO.add(pontoCarregamentoDTO);
        }

        return pontosCarregamentosDTO;
    }


    @PutMapping("/pontos-carregamento/{id}")
    public Integer atualizar(@PathVariable Long id, @RequestParam String status) {
        return pontoCarregamentoRepositorio.updateStatusById(id, status);
    }
    @GetMapping("/ponto/Taxa/{id}")
    public double getTaxa(@PathVariable Long id){
        return pontoCarregamentoRepositorio.findById(id).get().getTaxaOPC();
    }
    @GetMapping("/opc")
    public List<PontoCarregamento> getPontosProrios(@RequestParam String ownerEmail){
        return pontoCarregamentoRepositorio.findByOwnerEmail(ownerEmail);
    }
    @GetMapping("/opc/ponto")
    public Optional<PontoCarregamento> getPontoProrio(@RequestParam Long id){
        return pontoCarregamentoRepositorio.findById(id);
    }
    @PutMapping("/opc/ponto/editar")
    public PontoCarregamento editaPonto(@RequestParam Long id, @RequestParam String local, @RequestParam String estado, @RequestParam double capacidade, @RequestParam double taxaOPC)
    {
        PontoCarregamento pontoCarregamento = pontoCarregamentoRepositorio.getById(id);

        pontoCarregamento.setMaxCapacity(capacidade);
        pontoCarregamento.setLocal(local);
        pontoCarregamento.setEstado(estado);
        pontoCarregamento.setTaxaOPC(taxaOPC);

        return pontoCarregamentoRepositorio.save(pontoCarregamento);
    }
    @PostMapping("/opc/ponto/criar")
    public PontoCarregamento criarPonto(@RequestParam String email, @RequestParam String local, @RequestParam String estado, @RequestParam double capacidade, @RequestParam double taxaOPC){
        PontoCarregamento pontoCarregamento = new PontoCarregamento();

        pontoCarregamento.setMaxCapacity(capacidade);
        pontoCarregamento.setLocal(local);
        pontoCarregamento.setEstado(estado);
        pontoCarregamento.setOwnerEmail(email);
        pontoCarregamento.setTaxaOPC(taxaOPC);

        return pontoCarregamentoRepositorio.save(pontoCarregamento);
    }

    @DeleteMapping("/opc/ponto/eliminar")
    public ResponseEntity<String> eliminarPonto(@RequestParam Long id){
        try {
            pontoCarregamentoRepositorio.deleteById(id);
            return ResponseEntity.ok("Ponto de carregamento excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o ponto de carregamento");
        }
    }
}