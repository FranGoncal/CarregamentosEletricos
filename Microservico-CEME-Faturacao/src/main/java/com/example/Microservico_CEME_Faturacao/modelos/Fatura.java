package com.example.Microservico_CEME_Faturacao.modelos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sessaoId;
    private String emailUtilizadorId;
    private Long veiculoId;

    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "utilizador_id", nullable = false)
    private Utilizador utilizador;
    */
    private long consumoEnergia;
    private double custoTotal;
    //private String statusPagamento;


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

    public String getEmailUtilizadorId() {
        return emailUtilizadorId;
    }

    public void setEmailUtilizador(String emailUtilizadorId) {
        this.emailUtilizadorId = emailUtilizadorId;
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
