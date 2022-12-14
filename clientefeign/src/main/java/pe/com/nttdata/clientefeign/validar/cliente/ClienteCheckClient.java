package pe.com.nttdata.clientefeign.validar.cliente;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "validarcliente",
        url = "${clientefeign.validarcliente.url}"
)
public interface ClienteCheckClient {

    @GetMapping(path = "api/v1/cliente-check/{clienteId}")
    public ClienteCheckResponse validarCliente(@PathVariable("clienteId") Integer clienteId);

}
