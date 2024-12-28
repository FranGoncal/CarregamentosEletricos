package com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
//Mensagem enviada **(Simulacao->CemeFaturacao)**
public class SessionDetails {
    private Long sessionId;
    private Long energyConsumed;
    private Duration duration;
    private String userEmail;
    private Long veiculoId;
    private Long postoId;
    // Getters, Setters, Constructors


    public Long getPostoId() {
        return postoId;
    }

    public void setPostoId(Long postoId) {
        this.postoId = postoId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(Long energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }
}
