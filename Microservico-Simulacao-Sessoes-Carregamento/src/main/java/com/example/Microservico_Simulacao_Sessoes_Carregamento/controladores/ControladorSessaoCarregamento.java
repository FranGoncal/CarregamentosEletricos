package com.example.Microservico_Simulacao_Sessoes_Carregamento.controladores;


import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.SessaoCarregamento;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies.ProxyCemeFaturacao;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.SessionDetails;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies.ProxyOPC;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.repositorios.SessaoCarregamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class ControladorSessaoCarregamento {

    @Autowired
    SessaoCarregamentoRepositorio sessaoCarregamentoRepositorio;

    @Autowired
    ProxyCemeFaturacao proxyCemeFaturacao;

    @Autowired
    ProxyOPC proxyOPC;

    @PostMapping("/Simulacao/{idPosto}/{idVeiculo}/{idUtilizador}")
    public ResponseEntity<String> registrar(@PathVariable Long idPosto, @PathVariable Long idVeiculo, @PathVariable Long idUtilizador) {

        // verificar se posto ta DISPONIVEL
        if( !proxyOPC.consultarEstado(idPosto).equals("DISPONIVEL") )
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Posto Indisponivel");

        //ocupar o posto
        proxyOPC.atualizar(idPosto,"EM_USO");

        SessaoCarregamento sessaoCarregamento = new SessaoCarregamento();

        // TODO arranjar o consumo menor ou do posto ou do carro!!!
        sessaoCarregamento.setConsumoEnergia(666l);
        sessaoCarregamento.setIdPosto(idPosto);
        sessaoCarregamento.setTerminada(false);
        sessaoCarregamento.setIdVeiculo(idVeiculo);
        sessaoCarregamento.setIdUtilizador(idUtilizador);
        sessaoCarregamento.setInicio(LocalDateTime.now());

        sessaoCarregamentoRepositorio.save(sessaoCarregamento);

        return ResponseEntity.ok("Sessão criada com sucesso");
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
    public ResponseEntity<String> atualizar(@PathVariable Long id) {

        Optional<SessaoCarregamento> sessao = sessaoCarregamentoRepositorio.findById(id);

        if (sessao.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sessão não encontrada");
        }
        if (sessao.get().getTerminada())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sessão já foi terminada");

        sessao.get().atualizaDuracao();
        sessaoCarregamentoRepositorio.save(sessao.get());

        //Enviar info para o servico de CEME-faturacao
        SessionDetails sessionDetails = new SessionDetails();

        sessionDetails.setDuration(sessao.get().getDuracao());
        sessionDetails.setEnergyConsumed(sessao.get().getConsumoEnergia());

        sessionDetails.setSessionId(sessao.get().getId());
        sessionDetails.setVeiculoId(sessao.get().getIdVeiculo());
        sessionDetails.setUserId(sessao.get().getIdUtilizador());
        sessionDetails.setPostoId(sessao.get().getIdPosto());

        //desocupar o posto
        proxyOPC.atualizar(sessao.get().getIdPosto(),"DISPONIVEL");
        //Criar fatura
        proxyCemeFaturacao.criar(sessionDetails);
        sessaoCarregamentoRepositorio.updateStatusById(id, true);
        return ResponseEntity.ok("Sessão terminada com sucesso");
    }

}
