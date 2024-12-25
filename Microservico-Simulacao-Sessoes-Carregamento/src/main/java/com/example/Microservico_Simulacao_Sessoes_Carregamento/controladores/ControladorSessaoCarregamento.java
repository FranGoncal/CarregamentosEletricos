package com.example.Microservico_Simulacao_Sessoes_Carregamento.controladores;


import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.SessaoCarregamento;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.repositorios.SessaoCarregamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ControladorSessaoCarregamento {

    @Autowired
    SessaoCarregamentoRepositorio sessaoCarregamentoRepositorio;

    @PostMapping("/Simulacao/{idPosto}/{idVeiculo}")
    public SessaoCarregamento registrar(@PathVariable Long idPosto, @PathVariable Long idVeiculo) {
        SessaoCarregamento sessaoCarregamento = new SessaoCarregamento();

        sessaoCarregamento.setConsumoEnergia(0l);
        sessaoCarregamento.setIdPosto(idPosto);
        sessaoCarregamento.setTerminada(false);
        sessaoCarregamento.setIdVeiculo(idVeiculo);
        sessaoCarregamento.setInicio(LocalDateTime.now());

        sessaoCarregamentoRepositorio.save(sessaoCarregamento);
        return sessaoCarregamento;
    }
    @GetMapping("/Simulacao/{id}")
    public Optional<SessaoCarregamento> consultar(@PathVariable Long id) {

        Optional<SessaoCarregamento> sessao = sessaoCarregamentoRepositorio.findById(id);
        sessao.get().atualizaDuracao();
        sessaoCarregamentoRepositorio.save(sessao.get());

        return sessao;
    }
    @GetMapping("/Simulacao")
    public List<SessaoCarregamento> listar() {
        List<SessaoCarregamento> listaSessoes= sessaoCarregamentoRepositorio.findAll();

        for(SessaoCarregamento sessao : listaSessoes){
            sessao.atualizaDuracao();
            sessaoCarregamentoRepositorio.save(sessao);
        }

        return sessaoCarregamentoRepositorio.findAll();
    }

    @PutMapping("/Simulacao/terminar/{id}")
    public Integer atualizar(@PathVariable Long id) {

        Optional<SessaoCarregamento> sessao = sessaoCarregamentoRepositorio.findById(id);
        sessao.get().atualizaDuracao();
        sessaoCarregamentoRepositorio.save(sessao.get());

        return sessaoCarregamentoRepositorio.updateStatusById(id, true);
    }

}
