package com.example.Microservico_Utilizadores_Veiculos;

import com.example.Microservico_Utilizadores_Veiculos.modelos.Utilizador;
import com.example.Microservico_Utilizadores_Veiculos.modelos.Veiculo;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.UtilizadorRepositorio;
import com.example.Microservico_Utilizadores_Veiculos.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroservicoUtilizadoresVeiculosApplication implements ApplicationRunner{

	@Autowired
	UtilizadorRepositorio utilizadorRepositorio;

	@Autowired
	VeiculoRepositorio veiculoRepositorio;

	public static void main(String[] args) {
		SpringApplication.run(MicroservicoUtilizadoresVeiculosApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Utilizador u1 = new Utilizador();
		u1.setRole("Admin");
		u1.setName("aaa");
		u1.setEmail("al@gmail.com");
		u1.setPassword("aaa");

		Veiculo v1 = new Veiculo();
		v1.setAutonomia(111);
		v1.setBateria(111);
		v1.setMarca("bmw");
		v1.setModelo("i4");
		v1.setUtilizador(u1);

		u1.setVehicles(v1);

		utilizadorRepositorio.save(u1);
		veiculoRepositorio.save(v1);


	}
}
