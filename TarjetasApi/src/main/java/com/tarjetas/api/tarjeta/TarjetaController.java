package com.tarjetas.api.tarjeta;

import com.tarjetas.api.dolar.Dolar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/tarjetas")
public class TarjetaController {

    @Autowired
    private TarjetaService tarjetaService;

    @PostMapping("/agregar")
    public Tarjeta addTarjeta(@RequestBody Tarjeta tarjeta) {
        return tarjetaService.addTarjeta(tarjeta);
    }

    @GetMapping
    public List<Tarjeta> getTarjetas() {
        return tarjetaService.getTarjetas();
    }

    @GetMapping("/{id}")
    public Optional<Tarjeta> getTarjetaById(@PathVariable Long id) {
        return tarjetaService.getTarjetaById(id);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Tarjeta> getTarjetasByCliente(@PathVariable Long clienteId) {
        return tarjetaService.getTarjetasByCliente(clienteId);
    }

    @GetMapping("/cliente/{clienteId}/saldos")
    public Map<String, BigDecimal> getSaldosByCliente(@PathVariable Long clienteId) {
        return tarjetaService.getSaldosByCliente(clienteId);
    }

    @GetMapping("/cliente/{clienteId}/mayor-saldo")
    public Tarjeta getTarjetaMayorSaldo(@PathVariable Long clienteId) {
        return tarjetaService.getTarjetaMayorSaldo(clienteId);
    }

    @GetMapping("/estado/{estado}")
    public List<Tarjeta> getTarjetasByEstado(@PathVariable EstadoTarjeta estado) {
        return tarjetaService.getTarjetasByEstado(estado);
    }

    @PatchMapping("/{id}/estado/{nuevoEstado}")
    public Tarjeta updateEstado(@PathVariable Long id, @PathVariable EstadoTarjeta nuevoEstado) {
        return tarjetaService.updateEstado(id, nuevoEstado);
    }

    @DeleteMapping("/eliminar/{id}")
    public void deleteTarjeta(@PathVariable Long id) {
        tarjetaService.deleteById(id);
    }

    @GetMapping("/cotizacion/mep")
    public Dolar getCotizacion() {
        return tarjetaService.getCotizacion();
    }
}
