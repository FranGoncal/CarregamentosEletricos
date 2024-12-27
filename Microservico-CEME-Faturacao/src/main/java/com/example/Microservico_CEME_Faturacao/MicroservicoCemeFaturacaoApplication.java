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
		ceme1.setName("CEME 1");
		ceme1.setPrecoPorKWh(0.20);

		CEME ceme2 = new CEME();
		ceme2.setName("CEME 2");
		ceme2.setPrecoPorKWh(0.22);

		cemeRepositorio.save(ceme1);
		cemeRepositorio.save(ceme2);
	}
}