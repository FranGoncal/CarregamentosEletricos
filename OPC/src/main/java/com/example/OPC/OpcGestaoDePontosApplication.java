package com.example.OPC;

import com.example.OPC.modelos.PontoCarregamento;
import com.example.OPC.repositorios.PontoCarregamentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class OpcGestaoDePontosApplication implements ApplicationRunner {
	public static void main(String[] args) {
		SpringApplication.run(OpcGestaoDePontosApplication.class, args);
	}

	@Autowired
	PontoCarregamentoRepositorio pontoCarregamentoRepositorio;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		PontoCarregamento pontoCarregamento1 = new PontoCarregamento();
		PontoCarregamento pontoCarregamento2 = new PontoCarregamento();
		PontoCarregamento pontoCarregamento3 = new PontoCarregamento();

		pontoCarregamento1.setMaxCapacity(150);
		pontoCarregamento1.setStatus("disponivel");
		pontoCarregamento1.setOwnerEmail("a@a.a");
		pontoCarregamento1.setLocal("Carapalha");
		pontoCarregamento1.setTaxaOPC(0.4);

		pontoCarregamento2.setMaxCapacity(250);
		pontoCarregamento2.setStatus("disponivel");
		pontoCarregamento2.setOwnerEmail("a@a.a");
		pontoCarregamento2.setLocal("Carapalha");
		pontoCarregamento2.setTaxaOPC(0.7);

		pontoCarregamento3.setMaxCapacity(100);
		pontoCarregamento3.setStatus("disponivel");
		pontoCarregamento3.setOwnerEmail("o@o.o");
		pontoCarregamento3.setLocal("Carvalha");
		pontoCarregamento3.setTaxaOPC(0.6);

		pontoCarregamentoRepositorio.save(pontoCarregamento1);
		pontoCarregamentoRepositorio.save(pontoCarregamento2);
		pontoCarregamentoRepositorio.save(pontoCarregamento3);

	}
}
