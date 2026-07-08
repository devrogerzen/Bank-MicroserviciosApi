package com.Client.api;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
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
}
