package com.example.Microservico_Simulacao_Sessoes_Carregamento.modelos;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Duration;

@AllArgsConstructor
@NoArgsConstructor
//Mensagem enviada **(Simulacao->CemeFaturacao)**
public class SessionDetails {
    private Long sessionId;
    private double energyConsumed;
    private Duration duration;
    private String userEmail;
    private Long idCeme;
    private Long veiculoId;
    private Long postoId;
    // Getters, Setters, Constructors


    public Long getIdCeme() {
        return idCeme;
    }

    public void setIdCeme(Long idCeme) {
        this.idCeme = idCeme;
    }

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

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(double energyConsumed) {
        //arredondar 2 casa decimal
        this.energyConsumed = Math.ceil(energyConsumed * 100) / 100;
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
