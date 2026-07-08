package com.Client.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public List<Client> listarClientes() {
        return clientService.listarClientes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> obtenerCliente(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clientService.obtenerPorId(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Client> crearCliente(@RequestBody Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.crearCliente(client));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> actualizarCliente(@PathVariable Long id, @RequestBody Client client) {
        try {
            return ResponseEntity.ok(clientService.actualizarCliente(id, client));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        try {
            clientService.eliminarCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
