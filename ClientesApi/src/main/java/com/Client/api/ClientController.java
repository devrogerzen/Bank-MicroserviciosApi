package com.Client.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    // === CONSIGNA 1: Operaciones básicas con List ===

    @GetMapping("/activos")
    public List<Client> obtenerClientesActivos() {
        return clientService.obtenerClientesActivos();
    }

    @GetMapping("/nombres")
    public List<String> obtenerNombresCompletos() {
        return clientService.obtenerNombresCompletos();
    }

    @GetMapping("/ordenados")
    public List<Client> obtenerClientesOrdenadosPorApellido() {
        return clientService.obtenerClientesOrdenadosPorApellido();
    }

    // === CONSIGNA 2: Operaciones con Map ===

    @GetMapping("/{clienteId}/transacciones")
    public List<Transaccion> obtenerTransacciones(@PathVariable Long clienteId) {
        return clientService.obtenerTransacciones(clienteId);
    }

    @GetMapping("/saldos-totales")
    public Map<Long, Double> calcularSaldosTotalesPorCliente() {
        return clientService.calcularSaldosTotalesPorCliente();
    }

    @GetMapping("/mayor-saldo")
    public Long obtenerClienteConMayorSaldo() {
        return clientService.obtenerClienteConMayorSaldo();
    }

    // === CONSIGNA 3: Streams avanzados ===

    @GetMapping("/depositos")
    public List<Transaccion> obtenerTodosLosDepositos() {
        return clientService.obtenerTodosLosDepositos();
    }

    @GetMapping("/transacciones/existe-mayor-a")
    public boolean existeTransaccionMayorA(@RequestParam double umbral) {
        return clientService.existeTransaccionMayorA(umbral);
    }

    @GetMapping("/{clienteId}/transacciones/promedio")
    public double calcularPromedioTransacciones(@PathVariable Long clienteId) {
        return clientService.calcularPromedioTransacciones(clienteId);
    }
}
