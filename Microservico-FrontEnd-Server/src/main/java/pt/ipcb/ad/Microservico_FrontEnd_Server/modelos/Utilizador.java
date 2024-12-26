package pt.ipcb.ad.Microservico_FrontEnd_Server.modelos;

import com.fasterxml.jackson.annotation.JsonManagedReference;
//import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilizador {

    private Long id;
    private String name;
    private String email;
    private String password;
    //@Transient
    private String role;
    //@OneToMany(mappedBy = "utilizador", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    //@JsonBackReference
    private Set<Veiculo> vehicles = new HashSet<>();

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
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
    public void setVehicles(Veiculo veiculo) {
        vehicles.add(veiculo);
    }
}