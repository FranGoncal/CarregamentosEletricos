package com.example.Microservico_CEME_Faturacao;

import com.example.Microservico_CEME_Faturacao.modelos.CEME;
import com.example.Microservico_CEME_Faturacao.repositorios.CEMERepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicoCemeFaturacaoApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicoCemeFaturacaoApplication.class, args);
	}

	@Autowired
	CEMERepositorio cemeRepositorio;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		CEME ceme1 = new CEME();
		ceme1.setName("EDP");
		ceme1.setOwnerEmail("a@a.a");
		ceme1.setPrecoPorKWh(0.20);

		CEME ceme2 = new CEME();
		ceme2.setName("Fornecedor X");
		ceme2.setOwnerEmail("a@a.a");
		ceme2.setPrecoPorKWh(0.22);

		CEME ceme3 = new CEME();
		ceme3.setName("Elon Musk");
		ceme3.setOwnerEmail("c@c.c");
		ceme3.setPrecoPorKWh(0.30);

		cemeRepositorio.save(ceme1);
		cemeRepositorio.save(ceme2);
		cemeRepositorio.save(ceme3);
	}
}