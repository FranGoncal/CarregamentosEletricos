package com.example.Microservico_CEME_Faturacao.controladores;

import com.example.Microservico_CEME_Faturacao.modelos.CEME;
import com.example.Microservico_CEME_Faturacao.modelos.Fatura;
import com.example.Microservico_CEME_Faturacao.repositorios.CEMERepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
public class ControladorCEME {

    @Autowired
    CEMERepositorio cemeRepositorio;

    @GetMapping("/CEME/listar")//TODO verificar se a adicao de /listar nao estragou nada
    public List<CEME> listar() {
        return cemeRepositorio.findAll();
    }
    @GetMapping("/CEME/{id}")
    public double getPreco(@PathVariable Long id) {
        return cemeRepositorio.findById(id).get().getPrecoPorKWh();
    }
    @GetMapping("/CEME/{id}/nome")
    public String getNome(@PathVariable Long id) {
        return cemeRepositorio.findById(id).get().getName();
    }
    @GetMapping("/CEME")
    public List<CEME> getFornecedoresProrios(@RequestParam String ownerEmail){
        return cemeRepositorio.findByOwnerEmail(ownerEmail);
    }
    @GetMapping("/CEME/consultar")
    public CEME getCeme(@RequestParam Long id){
        return cemeRepositorio.findById(id).get();
    }

    @PutMapping("/CEME/editar")
    public CEME editaCeme(@RequestParam Long id,@RequestParam String name ,@RequestParam double precoPorKWh){
        CEME ceme = cemeRepositorio.findById(id).get();
        ceme.setName(name);
        ceme.setPrecoPorKWh(precoPorKWh);
        return cemeRepositorio.save(ceme);
    }

    @PostMapping("/CEME/criar")
    public CEME criarCeme(@RequestParam String email,@RequestParam String fornecedor,@RequestParam double precoPorKWH){
        CEME ceme = new CEME();
        ceme.setOwnerEmail(email);
        ceme.setName(fornecedor);
        ceme.setPrecoPorKWh(precoPorKWH);

        return cemeRepositorio.save(ceme);
    }

    @DeleteMapping("/CEME/eliminar")
    public ResponseEntity<String> eliminarCeme(@RequestParam Long id){
        try {
            cemeRepositorio.deleteById(id);
            return ResponseEntity.ok("Ceme excluído com sucesso");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao excluir o Ceme");
        }
    }

}
