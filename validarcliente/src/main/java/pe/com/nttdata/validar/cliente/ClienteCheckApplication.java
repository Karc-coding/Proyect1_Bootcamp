package pe.com.nttdata.validar.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@EnableFeignClients(
        basePackages = "pe.com.nttdata.clientefeign"
)
@EnableEurekaClient
@SpringBootApplication
public class ClienteCheckApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClienteCheckApplication.class, args);
    }
}
