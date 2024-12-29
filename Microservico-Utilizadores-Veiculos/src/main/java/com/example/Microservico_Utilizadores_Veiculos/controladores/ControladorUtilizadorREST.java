package com.example.Microservico_Utilizadores_Veiculos.controladores;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import com.example.Microservico_Utilizadores_Veiculos.modelos.UtilizadorDTO;
import com.example.Microservico_Utilizadores_Veiculos.modelos.Veiculo;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.UtilizadorRepositorio;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
public class ControladorUtilizadorREST {

    @Autowired
    UtilizadorRepositorio utilizadorRepositorio;

    @Autowired
    VeiculoRepositorio veiculoRepositorio;

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

    @GetMapping("/utilizadores/seguro")
    public List<UtilizadorDTO> listarSeguro() {
        List<Utilizador> users = utilizadorRepositorio.findAll();
        List<UtilizadorDTO> usersDTO = new ArrayList<>();

        for (Utilizador user : users){
            UtilizadorDTO userDTO = new UtilizadorDTO();
            userDTO.setId(user.getId());
            userDTO.setVehicles(user.getVehicles());
            userDTO.setRole(user.getRole());
            userDTO.setEmail(user.getEmail());
            userDTO.setName(user.getName());

            usersDTO.add(userDTO);
        }
        return usersDTO;
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
    @GetMapping("/utilizadores/emails")
    public List<String> consultarEmails() {
        List<String> emails = utilizadorRepositorio.findAllEmails();
        return emails;
    }

    @GetMapping("/utilizadores/id/")
    public Long getId(@RequestParam String email) {

        // ver se existe realmente este user
        Utilizador user = utilizadorRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User não encontrado!"));

        Optional<Utilizador> utilizador=utilizadorRepositorio.findByEmail(email);
        return utilizador.get().getId();
    }

    @GetMapping("/utilizador/seguro")
    public UtilizadorDTO getUserSeguro(@RequestParam Long id){
        Utilizador user = utilizadorRepositorio.getById(id);
        UtilizadorDTO userDTO = new UtilizadorDTO();
        //preparar informacoes do utilizador
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getName());
        userDTO.setRole(user.getRole());
        userDTO.setId(user.getId());
        userDTO.setVehicles(user.getVehicles());

        return userDTO;
    }
    @PutMapping("/utilizador/edicao")
    public ResponseEntity<String> editaUser(@RequestParam Long id, @RequestParam String nome, @RequestParam String role){
        Utilizador utilizador = utilizadorRepositorio.getById(id);
        utilizador.setName(nome);
        utilizador.setRole(role);
        utilizadorRepositorio.save(utilizador);

        return ResponseEntity.ok("Utilizador atualizado com sucesso");
    }

    @DeleteMapping("/utilizador/eliminar/{id}")
    public ResponseEntity<String> eliminarUser(@PathVariable Long id){
        utilizadorRepositorio.deleteById(id);
        return ResponseEntity.ok("Utilizador eliminado com sucesso");
    }

    @PutMapping("/utilizador/{id}/remover-veiculo/{idVeiculo}")
    public ResponseEntity<String> removerVeiculo(@RequestParam Long id, @RequestParam Long idVeiculo){
        Utilizador utilizador = utilizadorRepositorio.getById(id);
        Set<Veiculo> veiculos = utilizador.getVehicles();
        System.out.println("vou apagar!");
        Veiculo veiculo = veiculoRepositorio.findById(idVeiculo).get();
        veiculos.remove(veiculo);
        System.out.println("apaguei!");
        utilizador.setVeiculos(veiculos);
        utilizadorRepositorio.save(utilizador);

        return ResponseEntity.ok("Veiculo removido com sucesso");
    }

    @PutMapping("/utilizador/{email}/adiciona-veiculo")
    public ResponseEntity<String> adicionaVeiculo(@PathVariable String email, @RequestBody Veiculo veiculo){

        Utilizador user = utilizadorRepositorio.findByEmail(email).get();
        Set<Veiculo> veiculos = user.getVehicles();
        veiculos.add(veiculo);
        user.setVeiculos(veiculos);
        utilizadorRepositorio.save(user);

        return ResponseEntity.ok("Veiculo adicionado com sucesso");
    }

}