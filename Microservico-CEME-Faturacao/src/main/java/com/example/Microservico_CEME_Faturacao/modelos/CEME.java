package com.example.Microservico_CEME_Faturacao.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CEME {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ownerEmail;
    private Double precoPorKWh;
    private double taxaCEME;


    public double getTaxaCEME() {
        return taxaCEME;
    }

    public void setTaxaCEME(double taxaCEME) {
        this.taxaCEME = taxaCEME;
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
