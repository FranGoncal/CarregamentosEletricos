package com.example.Microservico_Utilizadores_Veiculos.controladores;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import com.example.Microservico_Utilizadores_Veiculos.modelos.Veiculo;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.UtilizadorRepositorio;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ControladorVeiculoREST {


    @Autowired
    VeiculoRepositorio veiculoRepositorio;

    @PostMapping("/veiculos")
    public Veiculo registrar(@RequestParam String marca, @RequestParam String modelo, @RequestParam Double bateria, @RequestParam Integer autonomia) {
        Veiculo veiculo = new Veiculo();

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setBateria(bateria);
        veiculo.setAutonomia(autonomia);

        veiculoRepositorio.save(veiculo);
        return veiculo;
    }
    @GetMapping("/veiculos/{id}")
    public Optional<Veiculo> consultar(@PathVariable Long id) {
        return veiculoRepositorio.findById(id);
    }
    @GetMapping("/veiculos")
    public List<Veiculo> listar() {
        return veiculoRepositorio.findAll();
    }
}
