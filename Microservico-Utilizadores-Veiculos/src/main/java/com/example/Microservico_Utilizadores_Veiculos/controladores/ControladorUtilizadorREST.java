package com.example.Microservico_Utilizadores_Veiculos.controladores;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.UtilizadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ControladorUtilizadorREST {

    @Autowired
    UtilizadorRepositorio utilizadorRepositorio;

    @PostMapping("/utilizadores/registar")
    public Utilizador registrar(@RequestParam String email, @RequestParam String nome, @RequestParam String password, @RequestParam String role) {
        Utilizador utilizador = new Utilizador();

        utilizador.setEmail(email);
        utilizador.setName(nome);
        utilizador.setPassword(password);
        utilizador.setRole(role);

        utilizadorRepositorio.save(utilizador);
        return utilizador;
    }
    @GetMapping("/utilizadores/{id}")
    public Optional<Utilizador> consultar(@PathVariable Long id) {
        return utilizadorRepositorio.findById(id);
    }
    @GetMapping("/utilizadores")
    public List<Utilizador> listar() {
        return utilizadorRepositorio.findAll();
    }
}
