package pt.ipcb.ad.API_Cloud_Gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCloudGatewayApplication.class, args);
	}


	/*
	* microservico frontend
	* localhost:8080/
	*
	* microservico frontend
	* http://localhost:8755/MICROSERVICO-FRONTEND-SERVER/
	*
	* micorservico faturacao (direto)
	* localhost:8020/faturas/bmw/i4/1000/25
	*
	* micorservico faturacao (gateway)
	* http://localhost:8755/MICROSERVICO-FATURACAO/faturas/bmw/i4/1000/25
	*
	* microservico
	*
	* */
}
