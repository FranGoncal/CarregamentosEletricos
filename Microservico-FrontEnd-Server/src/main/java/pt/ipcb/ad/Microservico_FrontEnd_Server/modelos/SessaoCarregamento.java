package pt.ipcb.ad.Microservico_FrontEnd_Server.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessaoCarregamento {

    private Long id;
    private Long idPosto;
    private Long idVeiculo;
    private String emailUtilizador;

    private double precoCeme;
    private Long idCeme;


    private double carregamento; //kW
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private Duration duracao;
    private Boolean terminada;

    public Long getIdCeme() {return idCeme; }

    public void setIdCeme(Long idCeme) { this.idCeme = idCeme; }

    public double getPrecoCeme() {
        return precoCeme;
    }

    public void setPrecoCeme(double precoCeme) {
        this.precoCeme = precoCeme;
    }

    public String getEmailUtilizador() {
        return emailUtilizador;
    }

    public void setEmailUtilizador(String emailUtilizador) {
        this.emailUtilizador = emailUtilizador;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdPosto() {
        return idPosto;
    }

    public void setIdPosto(Long idPosto) {
        this.idPosto = idPosto;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public double getCarregamento() {
        return carregamento;
    }

    public void setCarregamento(double carregamento) {
        this.carregamento = carregamento;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public void setTerminada(Boolean terminada) {
        this.terminada = terminada;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void atualizaFim() {
        this.fim = LocalDateTime.now();
    }
    public Duration getDuracao() {
            atualizaDuracao();
        return duracao;
    }
    public void atualizaDuracao() {
        if(!terminada) {
            atualizaFim();
            Duration duracao = Duration.between(inicio, fim);
            this.duracao = duracao;
        }
    }



    public Boolean getTerminada() {
        return terminada;
    }
}
