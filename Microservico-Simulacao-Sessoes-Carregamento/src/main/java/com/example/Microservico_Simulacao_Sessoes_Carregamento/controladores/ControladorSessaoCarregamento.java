package com.example.Microservico_Simulacao_Sessoes_Carregamento.controladores;


import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.SessaoCarregamento;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies.ProxyCemeFaturacao;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos.SessionDetails;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies.ProxyOPC;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies.ProxyUtilizadoresVeiculos;
import com.example.Microservico_Simulacao_Sessoes_Carregamento.repositorios.SessaoCarregamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
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
    ProxyUtilizadoresVeiculos proxyUtilizadoresVeiculos;

    @Autowired
    ProxyOPC proxyOPC;

    @PostMapping("/Simulacao/{idPosto}/{idVeiculo}/{emailUtilizador}/{cemeId}/{cargaMaxima}")
    public Long registrar(@PathVariable Long idPosto, @PathVariable Long idVeiculo, @PathVariable String emailUtilizador, @PathVariable Long cemeId, @PathVariable Long cargaMaxima) {

        double precoCeme = proxyCemeFaturacao.getPreco(cemeId);

        //ocupar o posto
        proxyOPC.atualizar(idPosto,"EM_USO");

        SessaoCarregamento sessaoCarregamento = new SessaoCarregamento();

        // arranjar a capacidadeCarregamento menor ou do ponto ou do carro
        double capacidadePonto = proxyOPC.consultarCapacidade(idPosto);
        double capacidadeVeiculo = proxyUtilizadoresVeiculos.capacidadeCarregamento(idVeiculo);

        if(capacidadeVeiculo < capacidadePonto) {
            sessaoCarregamento.setCarregamento(capacidadeVeiculo);
        }
        else {
            sessaoCarregamento.setCarregamento(capacidadePonto);
        }

        sessaoCarregamento.setIdPosto(idPosto);
        sessaoCarregamento.setTerminada(false);
        sessaoCarregamento.setPrecoCeme(precoCeme);
        sessaoCarregamento.setCargaMaxima(cargaMaxima);
        sessaoCarregamento.setIdVeiculo(idVeiculo);
        sessaoCarregamento.setIdCeme(cemeId);
        sessaoCarregamento.setEmailUtilizador(emailUtilizador);
        sessaoCarregamento.setInicio(LocalDateTime.now());

        sessaoCarregamentoRepositorio.save(sessaoCarregamento);

        return sessaoCarregamento.getId();
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

    @GetMapping("/Simulacao/utilizador/{emailUtilizador}")
    public List<SessaoCarregamento> getSimulacoesByIdUtilizador(@PathVariable String emailUtilizador) {
        List<SessaoCarregamento> listaSessoes= sessaoCarregamentoRepositorio.findByEmailUtilizadorOrderByIdDesc(emailUtilizador);

        /*for(SessaoCarregamento sessao : listaSessoes){
            sessao.atualizaDuracao();
            sessaoCarregamentoRepositorio.save(sessao);
        }*/

        return listaSessoes;
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
        //calcular energia consumida

        double horas = (double) sessao.get().getDuracao().getSeconds() / 3600;
        sessionDetails.setEnergyConsumed((sessao.get().getCarregamento() * horas));
        System.out.println(sessionDetails.getEnergyConsumed());

        sessionDetails.setIdCeme(sessao.get().getIdCeme());
        sessionDetails.setSessionId(sessao.get().getId());
        sessionDetails.setVeiculoId(sessao.get().getIdVeiculo());
        sessionDetails.setUserEmail(sessao.get().getEmailUtilizador());
        sessionDetails.setPostoId(sessao.get().getIdPosto());

        //obter sessao atual
        Optional<SessaoCarregamento> sessaoCarregamento = sessaoCarregamentoRepositorio.findById(id);

        Long idCarro = sessaoCarregamento.get().getIdVeiculo();

        double bateriaAtual = proxyUtilizadoresVeiculos.bateriaAtual(idCarro);
        double bateriaTotal = proxyUtilizadoresVeiculos.bateriaTotal(idCarro);
        double carregamento = sessaoCarregamento.get().getCarregamento();
        long duracaoSegundos = sessaoCarregamento.get().getDuracao().getSeconds();
        double duracaoHoras = duracaoSegundos / 3600.0;
        //Energia carregada (kWh)=Potencia de carregamento (kW)×Duracao (horas)
        double energiaCarregada = carregamento * duracaoHoras;
        double bateriaAposCarregamento = bateriaAtual + energiaCarregada;

        if (bateriaAposCarregamento > bateriaTotal){
            //atualizar bateria
            proxyUtilizadoresVeiculos.atualizaBateria(idCarro,bateriaTotal);

        }
        else{
            proxyUtilizadoresVeiculos.atualizaBateria(idCarro,bateriaAposCarregamento);
        }


        //desocupar o posto
        proxyOPC.atualizar(sessao.get().getIdPosto(),"disponivel");
        //Criar fatura
        proxyCemeFaturacao.criar(sessionDetails);
        sessaoCarregamentoRepositorio.updateStatusById(id, true);
        return ResponseEntity.ok("Sessão terminada com sucesso");
    }

    @GetMapping("/Simulacao/{id}/percentagemCarregamento")
    public int getPercentagemCarregamento(@PathVariable Long id) {

        //obter sessao atual
        Optional<SessaoCarregamento> sessaoCarregamento = sessaoCarregamentoRepositorio.findById(id);

        Long idCarro = sessaoCarregamento.get().getIdVeiculo();

        double bateriaAtual = proxyUtilizadoresVeiculos.bateriaAtual(idCarro);
        double bateriaTotal = proxyUtilizadoresVeiculos.bateriaTotal(idCarro);

        if(sessaoCarregamento.get().getTerminada())
            return (int) ((bateriaAtual*100)/bateriaTotal);

        double carregamento = sessaoCarregamento.get().getCarregamento();
        long duracaoSegundos = sessaoCarregamento.get().getDuracao().getSeconds();

        double duracaoHoras = duracaoSegundos / 3600.0;
        //Energia carregada (kWh)=Potencia de carregamento (kW)×Duracao (horas)
        double energiaCarregada = carregamento * duracaoHoras;



        double bateriaAposCarregamento = bateriaAtual + energiaCarregada;

        if (bateriaAposCarregamento > sessaoCarregamento.get().getCargaMaxima()*bateriaTotal/100){
            //atualizar bateria
            //proxyUtilizadoresVeiculos.atualizaBateria(idCarro,bateriaTotal);
            //terminar a sessao
            System.out.println(bateriaAposCarregamento);
            atualizar(id);
        }
        //proxyUtilizadoresVeiculos.atualizaBateria(idCarro,bateriaAposCarregamento);

        int percentagemCarregamento = (int) ((bateriaAposCarregamento*100)/bateriaTotal);
        return percentagemCarregamento;
    }


}
