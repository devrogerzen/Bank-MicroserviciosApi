package com.Client.api;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final TransaccionRepository transaccionRepository;

    public ClientService(ClientRepository clientRepository, TransaccionRepository transaccionRepository) {
        this.clientRepository = clientRepository;
        this.transaccionRepository = transaccionRepository;
    }

    public List<Client> listarClientes() {
        return clientRepository.findAll();
    }

    public Client obtenerPorId(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public Client crearCliente(Client client) {
        return clientRepository.save(client);
    }

    public Client actualizarCliente(Long id, Client clientActualizado) {
        Client existente = obtenerPorId(id);
        existente.setNombre(clientActualizado.getNombre());
        existente.setApellidoORazonSocial(clientActualizado.getApellidoORazonSocial());
        existente.setDocumentoOCuit(clientActualizado.getDocumentoOCuit());
        existente.setDireccion(clientActualizado.getDireccion());
        existente.setTelefono(clientActualizado.getTelefono());
        existente.setEmail(clientActualizado.getEmail());
        return clientRepository.save(existente);
    }

    public void eliminarCliente(Long id) {
        obtenerPorId(id);
        clientRepository.deleteById(id);
    }

    // === CONSIGNA 1: Operaciones básicas con List ===

    // TODO 1a — Retorna solo los clientes con activo == true
    public List<Client> obtenerClientesActivos() {
        return clientRepository.findAll()
                .stream()
                .filter(Client::isActivo)
                .collect(Collectors.toList());
    }

    // TODO 1b — Retorna lista de "Nombre Apellido" de cada cliente
    public List<String> obtenerNombresCompletos() {
        return clientRepository.findAll()
                .stream()
                .map(c -> c.getNombre() + " " + c.getApellidoORazonSocial())
                .collect(Collectors.toList());
    }

    // TODO 1c — Retorna clientes ordenados alfabéticamente por apellido
    public List<Client> obtenerClientesOrdenadosPorApellido() {
        return clientRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Client::getApellidoORazonSocial))
                .collect(Collectors.toList());
    }

    // === CONSIGNA 2: Operaciones con Map ===

    // TODO 2a — Retorna las transacciones de un cliente (lista vacía si no existe)
    public List<Transaccion> obtenerTransacciones(Long clienteId) {
        return transaccionRepository.findByClienteId(clienteId);
    }

    // TODO 2b — Map con clienteId -> suma total de montos
    public Map<Long, Double> calcularSaldosTotalesPorCliente() {
        Map<Long, Double> resultado = new HashMap<>();
        Map<Long, List<Transaccion>> agrupado = transaccionRepository.findAll()
                .stream()
                .collect(Collectors.groupingBy(Transaccion::getClienteId));

        agrupado.forEach((clienteId, lista) -> {
            double total = lista.stream()
                    .mapToDouble(Transaccion::getMonto)
                    .sum();
            resultado.put(clienteId, total);
        });
        return resultado;
    }

    // TODO 2c — Retorna el clienteId con mayor saldo total acumulado
    public Long obtenerClienteConMayorSaldo() {
        return calcularSaldosTotalesPorCliente()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    // === CONSIGNA 3: Streams avanzados ===

    // TODO 3a — Todos los depósitos de todos los clientes
    public List<Transaccion> obtenerTodosLosDepositos() {
        return transaccionRepository.findAll()
                .stream()
                .filter(t -> "DEPOSITO".equals(t.getTipo()))
                .collect(Collectors.toList());
    }

    // TODO 3b — true si existe alguna transacción con monto mayor al umbral
    public boolean existeTransaccionMayorA(double umbral) {
        return transaccionRepository.findAll()
                .stream()
                .anyMatch(t -> t.getMonto() > umbral);
    }

    // TODO 3c — Promedio de montos de las transacciones de un cliente
    public double calcularPromedioTransacciones(Long clienteId) {
        return transaccionRepository.findByClienteId(clienteId)
                .stream()
                .mapToDouble(Transaccion::getMonto)
                .average()
                .orElse(0);
    }
}
