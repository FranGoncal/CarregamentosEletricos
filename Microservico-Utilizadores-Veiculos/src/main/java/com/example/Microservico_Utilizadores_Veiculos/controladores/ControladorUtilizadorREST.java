package com.example.Microservico_Utilizadores_Veiculos.controladores;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import com.example.Microservico_Utilizadores_Veiculos.modelos.UtilizadorDTO;
import com.example.Microservico_Utilizadores_Veiculos.modelos.Veiculo;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.UtilizadorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class ControladorUtilizadorREST {

    @Autowired
    UtilizadorRepositorio utilizadorRepositorio;

    @PostMapping("/utilizadores")
    public ResponseEntity<String> registrar(@RequestParam String email, @RequestParam String nome, @RequestParam String password, @RequestParam String role) {
        Utilizador utilizador = new Utilizador();

        utilizador.setEmail(email);
        utilizador.setName(nome);
        utilizador.setPassword(password);
        utilizador.setRole(role);

        System.out.println(role);

        utilizadorRepositorio.save(utilizador);
        return ResponseEntity.ok("Utilizador criado com sucesso");
    }
    @GetMapping("/utilizadores/{id}")
    public Optional<Utilizador> consultar(@PathVariable Long id) {
        return utilizadorRepositorio.findById(id);
    }
    @GetMapping("/utilizadores")
    public List<Utilizador> listar() {
        return utilizadorRepositorio.findAll();
    }

    @GetMapping("/utilizadores/autenticacao")
    public UtilizadorDTO autenticacao(@RequestParam String email, @RequestParam String password) {

        // ver se existe realmente este user
        Utilizador user = utilizadorRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Credenciais incorretas!"));

        //confirmar password
        if (!user.getPassword().equals(password)) {
            throw new BadCredentialsException("Credenciais incorretas!");
        }
        UtilizadorDTO userDTO = new UtilizadorDTO();

        //preparar informacoes do utilizador
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setId(user.getId());
        userDTO.setVehicles(user.getVehicles());

        return userDTO;
    }

    @GetMapping("/utilizadores/veiculos")
    public Set<Veiculo> consultarVeiculos(@RequestParam String email) {
        Optional<Utilizador> user = utilizadorRepositorio.findByEmail(email);
        return user.get().getVehicles();
    }

    @GetMapping("/utilizadores/id/")
    public Long getId(@RequestParam String email) {

        // ver se existe realmente este user
        Utilizador user = utilizadorRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User não encontrado!"));

        Optional<Utilizador> utilizador=utilizadorRepositorio.findByEmail(email);
        return utilizador.get().getId();
    }


}