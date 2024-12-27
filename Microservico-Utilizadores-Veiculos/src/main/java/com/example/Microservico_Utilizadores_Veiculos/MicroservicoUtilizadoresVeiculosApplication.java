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
		Utilizador u2 = new Utilizador();
		Utilizador u3 = new Utilizador();
		Utilizador u4 = new Utilizador();
		u1.setRole("ROLE_ADMIN");
		u1.setName("aaa");
		u1.setEmail("a@a.a");
		u1.setPassword("a");

		u2.setRole("ROLE_STANDARD");
		u2.setName("b");
		u2.setEmail("b@b.b");
		u2.setPassword("b");

		u3.setRole("ROLE_CEME");
		u3.setName("c");
		u3.setEmail("c@c.c");
		u3.setPassword("c");

		u4.setRole("ROLE_OPC");
		u4.setName("o");
		u4.setEmail("o@o.o");
		u4.setPassword("o");

		Veiculo v1 = new Veiculo();
		v1.setAutonomia(111);
		v1.setBateria(111);
		v1.setMarca("bmw");
		v1.setModelo("i4");
		v1.setUtilizador(u1);

		Veiculo v2 = new Veiculo();
		v2.setAutonomia(100);
		v2.setBateria(350);
		v2.setMarca("tesla");
		v2.setModelo("model x");
		v2.setUtilizador(u2);

		Veiculo v3 = new Veiculo();
		v3.setAutonomia(82);
		v3.setBateria(350);
		v3.setMarca("tesla");
		v3.setModelo("model y");
		v3.setUtilizador(u1);

		u1.setVehicles(v1);
		u1.setVehicles(v3);
		u2.setVehicles(v2);

		utilizadorRepositorio.save(u1);
		utilizadorRepositorio.save(u2);
		utilizadorRepositorio.save(u3);
		utilizadorRepositorio.save(u4);
		veiculoRepositorio.save(v1);
		veiculoRepositorio.save(v2);
		veiculoRepositorio.save(v3);



	}
}
