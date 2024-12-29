package com.example.Microservico_Simulacao_Sessoes_Carregamento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicoSimulacaoSessoesCarregamentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicoSimulacaoSessoesCarregamentoApplication.class, args);
	}

}