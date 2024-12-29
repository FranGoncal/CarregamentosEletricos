package com.example.Microservico_Utilizadores_Veiculos.modelos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UtilizadorDTO {

    private Long id;
    private String name;
    private String email;
    private String role;
    private Set<Veiculo> vehicles = new HashSet<>();

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Set<Veiculo> getVehicles() {
        return vehicles;
    }

    public void setVehicles(Set<Veiculo> vehicles) {
        this.vehicles = vehicles;
    }
}
