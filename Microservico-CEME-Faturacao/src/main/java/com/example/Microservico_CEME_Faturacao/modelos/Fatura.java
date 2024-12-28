package com.example.Microservico_CEME_Faturacao.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessaoId;
    private String emailUtilizador;
    private Long veiculoId;
    private Long idCeme;
    private LocalDateTime dataEmitida;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilizador_id", nullable = false)
    private Utilizador utilizador;
    */
    private long consumoEnergia;
    private double custoTotal;
    //private String statusPagamento;


    public Long getIdCeme() {
        return idCeme;
    }

    public void setIdCeme(Long idCeme) {
        this.idCeme = idCeme;
    }

    public LocalDateTime getDataEmitida() {
        return dataEmitida;
    }

    public void setDataEmitida(LocalDateTime dataEmitida) {
        this.dataEmitida = dataEmitida;
    }

    public Long getSessaoId() {
        return sessaoId;
    }

    public void setSessaoId(Long sessaoId) {
        this.sessaoId = sessaoId;
    }

    public long getConsumoEnergia() {
        return consumoEnergia;
    }

    public void setConsumoEnergia(long consumoEnergia) {
        this.consumoEnergia = consumoEnergia;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public String getEmailUtilizador() {
        return emailUtilizador;
    }

    public void setEmailUtilizador(String emailUtilizadorId) {
        this.emailUtilizador = emailUtilizadorId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    /*
    public Utilizador getUtilizador() {
        return utilizador;
    }

    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
    */
    public double getCustoTotal() {
        return custoTotal;
    }

    public void setCustoTotal(double custoTotal) {
        this.custoTotal = custoTotal;
    }
}
