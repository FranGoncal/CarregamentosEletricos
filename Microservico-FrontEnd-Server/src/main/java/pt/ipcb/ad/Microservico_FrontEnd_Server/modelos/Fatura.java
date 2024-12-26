package pt.ipcb.ad.Microservico_FrontEnd_Server.modelos;


/*(marca, modelo, KmP, KC)*/
/*(Calculos: Consumo euros, consumo medio kwh)*/
public class Fatura {

    Long id;
    String marca;
    String modelo;
    Double kmP;
    Double KC;
    //total fatura
    Double fatura;
    Double consumoMedio;

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
        return kmP;
    }

    public void setKmP(Double kmP) {
        this.kmP = kmP;
    }

    public Double getKC() {
        return KC;
    }

    public void setKC(Double KC) {
        this.KC = KC;
    }

    public Double getFatura() {
        return fatura;
    }

    public void setFatura(Double fatura) {
        this.fatura = fatura;
    }

    public Double getConsumoMedio() {
        return consumoMedio;
    }

    public void setConsumoMedio(Double consumoMedio) {
        this.consumoMedio = consumoMedio;
    }

    public Fatura(Long id, String marca, String modelo, Double kmP, Double KC, Double fatura, Double consumoMedio) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.kmP = kmP;
        this.KC = KC;
        this.fatura = fatura;
        this.consumoMedio = consumoMedio;
    }
}
