package pt.ipcb.ad.Microservico_FrontEnd_Server.modelos;

import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {
    private Long id;
    private String marca;
    private String modelo;
    private double bateria;                 //bateria total                         kWh
    private double bateriaAtual;            //bateria atual                         kWh
    private double capacidadeCarregamento;  //capacidade de carregar                kW

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    @JsonBackReference
    //@JsonManagedReference
    private Utilizador utilizador;

    public double getBateriaAtual() {
        return bateriaAtual;
    }
    public void setBateriaAtual(double bateriaAtual) {
        this.bateriaAtual = bateriaAtual;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }
    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
    public double getBateria() {
        return bateria;
    }
    public void setBateria(double bateria) {
        this.bateria = bateria;
    }
    public double getCapacidadeCarregamento() {
        return capacidadeCarregamento;
    }
    public void setCapacidadeCarregamento(double capacidadeCarregamento) {
        this.capacidadeCarregamento = capacidadeCarregamento;
    }
    public Utilizador getUtilizador() {
        return utilizador;
    }
    public void setUtilizador(Utilizador utilizador) {
        this.utilizador = utilizador;
    }
}
