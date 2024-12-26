package pt.ipcb.ad.Microservico_FrontEnd_Server.controladores;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DadosFaturaDTO {
    String marca;
    String modelo;
    Double KmP;
    Double KC;

    public DadosFaturaDTO(String marca, String modelo, Double kmP, Double KC) {
        this.marca = marca;
        this.modelo = modelo;
        KmP = kmP;
        this.KC = KC;
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

    public Double getKmP() {
        return KmP;
    }

    public void setKmP(Double kmP) {
        KmP = kmP;
    }

    public Double getKC() {
        return KC;
    }

    public void setKC(Double KC) {
        this.KC = KC;
    }
}
