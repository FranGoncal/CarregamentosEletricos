package pt.ipcb.ad.Microservico_FrontEnd_Server.modelos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoCarregamento {
    private Long id;
    private String local;
    private String ownerEmail; //email da pessoa que faz a gestao deste ponto
    private double maxCapacity; //capacidade de carregamento maxima -> kWh
    private String estado;
    private double taxaOPC; //taxa aplicada na fatura pelo uso do posto


    public double getTaxaOPC() {
        return taxaOPC;
    }
    public void setTaxaOPC(double taxaOPC) {
        this.taxaOPC = taxaOPC;
    }
    public String getOwnerEmail() {
        return ownerEmail;
    }
    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public double getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(double maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public String getStatus() {
        return estado;
    }

    public void setStatus(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
