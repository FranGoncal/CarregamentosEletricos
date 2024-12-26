package pt.est.ad.naming_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class NamingServerApplication {
	//TP_A_CORRER
	public static void main(String[] args) {
		SpringApplication.run(NamingServerApplication.class, args);
	}
	//http://localhost:8761/
}
