package pt.ipcb.ad.Microservico_FrontEnd_Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MicroservicoFrontEndServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicoFrontEndServerApplication.class, args);
	}

}
