package pt.ipcb.ad.Microservico_FrontEnd_Server.modelos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CEME {
    private Long id;
    private String name;
    private Double precoPorKWh;
    private String ownerEmail;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrecoPorKWh() {
        return precoPorKWh;
    }

    public void setPrecoPorKWh(Double precoPorKWh) {
        this.precoPorKWh = precoPorKWh;
    }
}
