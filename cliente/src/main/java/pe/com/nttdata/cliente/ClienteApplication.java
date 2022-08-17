package pe.com.nttdata.cliente;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@EnableFeignClients(
        basePackages = "pe.com.nttdata.clientefeign"
)
@PropertySources({
        @PropertySource("classpath:clientefeign-${spring.profiles.active}.properties")
})
@EnableEurekaClient
@SpringBootApplication( scanBasePackages = {"pe.com.nttdata.cliente", "pe.com.nttdata.clientequeues"})
public class ClienteApplication {
    public static void main(String[] args) {
        SpringApplication.run(ClienteApplication.class, args);
    }
}
