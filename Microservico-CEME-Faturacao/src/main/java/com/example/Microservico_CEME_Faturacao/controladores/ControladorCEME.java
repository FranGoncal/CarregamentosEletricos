package com.example.Microservico_CEME_Faturacao.controladores;

import com.example.Microservico_CEME_Faturacao.modelos.CEME;
import com.example.Microservico_CEME_Faturacao.modelos.Fatura;
import com.example.Microservico_CEME_Faturacao.repositorios.CEMERepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class ControladorCEME {

    @Autowired
    CEMERepositorio cemeRepositorio;

    @GetMapping("/CEME")
    public List<CEME> listar() {
        return cemeRepositorio.findAll();
    }
    @GetMapping("/CEME/{id}")
    public double getPreco(@PathVariable Long id) {
        return cemeRepositorio.findById(id).get().getPrecoPorKWh();
    }


}
