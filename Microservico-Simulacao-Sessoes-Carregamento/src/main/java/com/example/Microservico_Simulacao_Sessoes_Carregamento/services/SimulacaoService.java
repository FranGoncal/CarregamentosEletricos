package com.example.Microservico_Simulacao_Sessoes_Carregamento.services;

import com.example.Microservico_Simulacao_Sessoes_Carregamento.proxies.ProxyCemeFaturacao;
import org.springframework.stereotype.Service;

//Desnecessario

@Service
public class SimulacaoService {
    private final ProxyCemeFaturacao proxyCemeFaturacao;

    public SimulacaoService(ProxyCemeFaturacao cemeFaturacaoClient) {
        this.proxyCemeFaturacao = cemeFaturacaoClient;
    }

    public void endSession(Long sessionId,Long userId,Long veiculoId) {
        // Simula a busca dos detalhes da sessão
        //SessionDetails sessionDetails = new SessionDetails(sessionId, 20.5, 2.0, userId,veiculoId);

        // Notifica o microserviço CEME
        //cemeFaturacaoProxy.notifySessionEnd(sessionId, sessionDetails);
    }

}
