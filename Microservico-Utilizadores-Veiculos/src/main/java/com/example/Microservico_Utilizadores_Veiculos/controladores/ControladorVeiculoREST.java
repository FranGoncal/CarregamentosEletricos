package com.example.Microservico_Utilizadores_Veiculos.controladores;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import com.example.Microservico_Utilizadores_Veiculos.modelos.Veiculo;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.UtilizadorRepositorio;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ControladorVeiculoREST {


    @Autowired
    VeiculoRepositorio veiculoRepositorio;

    @Autowired
    UtilizadorRepositorio utilizadorRepositorio;

    @PutMapping("/veiculos")
    public Veiculo registrar(@RequestParam String marca, @RequestParam String modelo, @RequestParam double bateria, @RequestParam double bateriaAtual, @RequestParam double capacidadeCarregamento, @RequestParam Long id) {

        Utilizador utilizador = utilizadorRepositorio.findById(id).get();

        Veiculo veiculo = new Veiculo();

        veiculo.setMarca(marca);
        veiculo.setModelo(modelo);
        veiculo.setBateria(bateria);
        veiculo.setCapacidadeCarregamento(capacidadeCarregamento);
        veiculo.setBateriaAtual(bateriaAtual);
        veiculo.setUtilizador(utilizador);

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

    @GetMapping("/veiculos/{id}/capacidadeCarregamento")
    public double capacidadeCarregamento(@PathVariable Long id) {
        return veiculoRepositorio.findById(id).get().getCapacidadeCarregamento();
    }
    @GetMapping("/veiculos/{id}/bateriaAtual")
    public double bateriaAtual(@PathVariable Long id) {
        return veiculoRepositorio.findById(id).get().getBateriaAtual();
    }
    @GetMapping("/veiculos/{id}/bateriaTotal")
    public double bateriaTotal(@PathVariable Long id) {
        return veiculoRepositorio.findById(id).get().getBateria();
    }
    @PutMapping("/veiculos/{id}/bateria")
    public ResponseEntity<Double> atualizaBateria(@PathVariable Long id, @RequestParam double bateria) {
        Veiculo veiculo = veiculoRepositorio.findById(id).get();
        if (veiculo == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        veiculo.setBateriaAtual(bateria);
        veiculoRepositorio.save(veiculo);
        return new ResponseEntity<>(veiculo.getBateriaAtual(), HttpStatus.OK);
    }
    @GetMapping("/veiculos/{id}/nome")
    public String getNome(@PathVariable Long id) {
        Veiculo veiculo = veiculoRepositorio.findById(id).get();
        return veiculo.getMarca()+" "+veiculo.getModelo();
    }
    @DeleteMapping("/veiculos/{id}/apagar")
    public ResponseEntity<String> apagarVeiculo(@PathVariable Long id) {
        veiculoRepositorio.deleteById(id);
        return ResponseEntity.ok("Veiculo removido com sucesso");
    }
    @PutMapping("/veiculos/{idVeiculo}/editar")
    public ResponseEntity<String> editaVeiculo(@PathVariable Long idVeiculo,
                                               @RequestParam String marca,
                                               @RequestParam String modelo,
                                               @RequestParam double bateria,
                                               @RequestParam double bateriaAtual,
                                               @RequestParam double capacidadeCarregamento){
        Veiculo veiculo = consultar(idVeiculo).get();

        veiculo.setBateriaAtual(bateriaAtual);
        veiculo.setBateria(bateria);
        veiculo.setModelo(modelo);
        veiculo.setMarca(marca);
        veiculo.setCapacidadeCarregamento(capacidadeCarregamento);

        veiculoRepositorio.save(veiculo);
        return ResponseEntity.ok("Veiculo editado com sucesso");
    }

}
