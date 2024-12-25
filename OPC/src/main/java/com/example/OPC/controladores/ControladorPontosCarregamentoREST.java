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

    @PostMapping("/registar")
    public PontoCarregamento registrar(@RequestParam String local, @RequestParam double maxCapacidade) {
        PontoCarregamento pontoCarregamento = new PontoCarregamento();
        pontoCarregamento.setLocal(local);
        pontoCarregamento.setMaxCapacity(maxCapacidade);
        pontoCarregamentoRepositorio.save(pontoCarregamento);
        return pontoCarregamento;
    }
    @GetMapping("/consultar/{id}")
    public Optional<PontoCarregamento> consultar(@PathVariable Long id) {
        return pontoCarregamentoRepositorio.findById(id);
    }
    @PutMapping("/atualizar/{id}")
    public Integer atualizar(@PathVariable Long id, @RequestParam boolean status) {
        return pontoCarregamentoRepositorio.updateStatusById(id, status);
    }
    @GetMapping("/listar")
    public List<PontoCarregamento> listar(@RequestParam String local) {
        return pontoCarregamentoRepositorio.findByLocalContaining(local);
    }
}