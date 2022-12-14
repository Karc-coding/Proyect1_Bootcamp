package pe.com.nttdata.cliente.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.com.nttdata.cliente.model.Cliente;
import pe.com.nttdata.cliente.service.IClienteService;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("api/v1/cliente")
public class ClienteController {

    private final IClienteService clienteService;

    @GetMapping
    public ResponseEntity<?> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        log.info("listar clientes");
        return new ResponseEntity<>(clientes, clientes.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtenerCliente(@PathVariable("id") Integer id) {
        log.info("obtener cliente: ", id);
        return new ResponseEntity<>(clienteService.obtenerCliente(id), HttpStatus.OK);
    }

    @GetMapping(value = "/name/{nombre}")
    public ResponseEntity<?> obtenerClientesPorNombre(@PathVariable("nombre") String nombre) {
        log.info("obtener cliente por nombre: ", nombre);
        return new ResponseEntity<>(clienteService.listarClientesPorNombre(nombre), HttpStatus.OK);
    }

    @GetMapping(value = "/apellidoPaterno/{apePaterno}")
    public ResponseEntity<?> obtenerClientesPorApellidoPaterno(@PathVariable("apePaterno") String apePaterno) {
        log.info("obtener cliente por apellido paterno: ", apePaterno);
        return new ResponseEntity<>(clienteService.listarClientesPorApellidoPaterno(apePaterno), HttpStatus.OK);
    }

    @GetMapping(value = "/apellidoMaterno/{apeMaterno}")
    public ResponseEntity<?> obtenerClientesPorApellidoMaterno(@PathVariable("apeMaterno") String apeMaterno) {
        log.info("obtener cliente por apellido materno: ", apeMaterno);
        return new ResponseEntity<>(clienteService.listarClientesPorApellidoMaterno(apeMaterno), HttpStatus.OK);
    }

    @GetMapping(params="fechaNacimiento")
    public ResponseEntity<?> listarClientesPorFechaNacimiento(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaNacimiento) {
        List<Cliente> clientes = clienteService.listarClientesPorFechaNacimiento(fechaNacimiento);
        log.info("listar clientes por fecha de nacimiento: ", fechaNacimiento);
        return new ResponseEntity<>(clientes, clientes.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    /*@GetMapping(value = "/fechaNacimiento/{fechaNac}")
    public ResponseEntity<?> obtenerClientesPorFechaNacimiento(@PathVariable("fechaNac") String fechaNac) {
        Date fecha = null;
        log.info("obtener cliente por fecha nacimiento: ", fechaNac);
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            fecha = format.parse(fechaNac);
        } catch (ParseException e){
            e.getMessage();
        }
        return new ResponseEntity<>(clienteService.listarClientesPorFechaNacimiento(fecha), HttpStatus.OK);
    }*/


    /*@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registrarCliente(@RequestBody ClienteRequest clienteRequest) {
        log.info("nuevo registro de cliente {}", clienteRequest);
        Cliente cliente = clienteService.registrarCliente(clienteRequest);
        return new ResponseEntity<ClienteRequest>(new ClienteRequest(cliente.getId(), clienteRequest.nombre(), clienteRequest.apellidoPaterno(), clienteRequest.apellidoMaterno() , clienteRequest.email(), clienteRequest.fechaNacimiento()), HttpStatus.OK);
    }*/

    /*@PostMapping
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody Cliente cliente) {
        log.info("nuevo registro de cliente {}", cliente);
        Cliente newCliente = clienteService.registrarCliente(cliente);
        return new ResponseEntity<ClienteRequest>(new ClienteRequest(newCliente.getId(), cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno() , cliente.getEmail(), cliente.getFechaNacimiento()), HttpStatus.OK);
    }*/

    @PostMapping
    public ResponseEntity<?> registrarCliente(@Valid @RequestBody Cliente cliente) {
        log.info("nuevo registro de cliente {}", cliente);
        Cliente newCliente = clienteService.registrarCliente(cliente);
        String resultado = clienteService.validarCliente(newCliente);
        log.info("Resultado: " + resultado);
        if (resultado.equals("OK")){
            clienteService.registrarNotificacion(newCliente);
            clienteService.registrarNotificacionKafka(newCliente);
            return new ResponseEntity<ClienteRequest>(new ClienteRequest(newCliente.getId(), cliente.getNombre(), cliente.getApellidoPaterno(), cliente.getApellidoMaterno() , cliente.getEmail(), cliente.getFechaNacimiento()), HttpStatus.OK);
        }
        return new ResponseEntity("Servicio validarCliente no disponible", HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> modificarCliente(@RequestBody ClienteRequest clienteRequest) {
        log.info("modificar datos de cliente {}", clienteRequest);
        clienteService.modificarCliente(clienteRequest);
        return new ResponseEntity<ClienteRequest>(clienteRequest, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public void eliminarCliente(@PathVariable("id") Integer id) {
        log.info("eliminar cliente: ", id);
        clienteService.eliminarCliente(id);
    }

}
