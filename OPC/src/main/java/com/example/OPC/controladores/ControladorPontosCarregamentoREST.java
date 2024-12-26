package com.example.OPC.controladores;

import com.example.OPC.modelos.PontoCarregamento;
import com.example.OPC.repositorios.PontoCarregamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Optional<PontoCarregamento> consultar(@PathVariable Long id) {
        return pontoCarregamentoRepositorio.findById(id);
    }

    @GetMapping("/pontos-carregamento/estado/{id}")
    public String consultarEstado(@PathVariable Long id) {
        return pontoCarregamentoRepositorio.findById(id).get().getStatus();
    }

    @GetMapping("/pontos-carregamento")
    public List<PontoCarregamento> listar(@RequestParam String local) {
        return pontoCarregamentoRepositorio.findByLocalContaining(local);
    }
    @PutMapping("/pontos-carregamento/{id}")
    public Integer atualizar(@PathVariable Long id, @RequestParam String status) {
        return pontoCarregamentoRepositorio.updateStatusById(id, status);
    }

}