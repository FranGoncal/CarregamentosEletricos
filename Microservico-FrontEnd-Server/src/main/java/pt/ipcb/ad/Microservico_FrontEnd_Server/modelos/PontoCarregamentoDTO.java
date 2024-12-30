package pt.ipcb.ad.Microservico_FrontEnd_Server.modelos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class PontoCarregamentoDTO {

    private Long id;
    private String local;
    private double maxCapacity; //capacidade de carregamento maxima -> kWh
    private String estado;
    private double taxaOPC; //taxa aplicada na fatura pelo uso do posto


    public double getTaxaOPC() {
        return taxaOPC;
    }
    public void setTaxaOPC(double taxaOPC) {
        this.taxaOPC = taxaOPC;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
