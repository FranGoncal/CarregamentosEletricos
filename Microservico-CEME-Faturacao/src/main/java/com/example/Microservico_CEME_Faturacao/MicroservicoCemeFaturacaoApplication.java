package com.example.Microservico_CEME_Faturacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicoCemeFaturacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicoCemeFaturacaoApplication.class, args);
	}

}