package com.tarjetas.api.tarjeta;

import com.tarjetas.api.dolar.Dolar;
import com.tarjetas.api.dolar.DolarClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TarjetaService {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Autowired
    private DolarClient dolarClient;

    public List<Tarjeta> getTarjetas() {
        return tarjetaRepository.findAll();
    }

    public Optional<Tarjeta> getTarjetaById(Long id) {
        return tarjetaRepository.findById(id);
    }

    public List<Tarjeta> getTarjetasByCliente(Long clienteId) {
        return tarjetaRepository.findByClienteId(clienteId);
    }

    public List<Tarjeta> getTarjetasByEstado(EstadoTarjeta estado) {
        return tarjetaRepository.findByEstado(estado);
    }

    public Tarjeta addTarjeta(Tarjeta tarjeta) {
        return tarjetaRepository.save(tarjeta);
    }

    public Tarjeta updateEstado(Long id, EstadoTarjeta nuevoEstado) {
        Tarjeta tarjeta = tarjetaRepository.findById(id)
                .orElseThrow(() -> new TarjetaNotFoundException("Tarjeta no encontrada con id: " + id));
        tarjeta.setEstado(nuevoEstado);
        return tarjetaRepository.save(tarjeta);
    }

    public void deleteById(Long id) {
        tarjetaRepository.deleteById(id);
    }

    public Map<String, BigDecimal> getSaldosByCliente(Long clienteId) {
        return tarjetaRepository.findByClienteId(clienteId)
                .stream()
                .collect(Collectors.toMap(
                        Tarjeta::getNumeroTarjeta,
                        Tarjeta::getSaldoDisponible
                ));
    }

    public Tarjeta getTarjetaMayorSaldo(Long clienteId) {
        Map<String, BigDecimal> saldos = getSaldosByCliente(clienteId);

        String numeroTarjetaMax = saldos.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElseThrow(() -> new TarjetaNotFoundException(
                        "No se encontraron tarjetas para el cliente: " + clienteId));

        return tarjetaRepository.findByClienteId(clienteId)
                .stream()
                .filter(t -> t.getNumeroTarjeta().equals(numeroTarjetaMax))
                .findFirst()
                .orElseThrow(() -> new TarjetaNotFoundException(
                        "Tarjeta no encontrada: " + numeroTarjetaMax));
    }

    public Dolar getCotizacion() {
        return dolarClient.getDolarMep();
    }
}
