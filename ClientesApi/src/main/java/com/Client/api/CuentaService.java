package com.Client.api;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final DolarService dolarService;

    public CuentaService(CuentaRepository cuentaRepository, DolarService dolarService) {
        this.cuentaRepository = cuentaRepository;
        this.dolarService = dolarService;
    }

    public List<CuentaResponse> listarCuentas() {
        List<Cuenta> cuentas = cuentaRepository.findAll();
        DolarResponse mep = dolarService.obtenerDolarMep();
        Double cotizacion = mep.getCompra();

        return cuentas.stream().map(cuenta -> {
            CuentaResponse response = new CuentaResponse();
            response.setId(cuenta.getId());
            response.setNumeroCuenta(cuenta.getNumeroCuenta());
            response.setSaldo(cuenta.getSaldo());
            response.setMoneda(cuenta.getMoneda());

            if ("USD".equalsIgnoreCase(cuenta.getMoneda())) {
                response.setCotizacionMepCompra(cotizacion);
                response.setSaldoEnPesos(cuenta.getSaldo() * cotizacion);
            } else {
                response.setSaldoEnPesos(cuenta.getSaldo());
                response.setCotizacionMepCompra(null);
            }
            return response;
        }).toList();
    }

    public CuentaResponse obtenerPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException(id));

        CuentaResponse response = new CuentaResponse();
        response.setId(cuenta.getId());
        response.setNumeroCuenta(cuenta.getNumeroCuenta());
        response.setSaldo(cuenta.getSaldo());
        response.setMoneda(cuenta.getMoneda());

        if ("USD".equalsIgnoreCase(cuenta.getMoneda())) {
            DolarResponse mep = dolarService.obtenerDolarMep();
            Double cotizacion = mep.getCompra();
            response.setCotizacionMepCompra(cotizacion);
            response.setSaldoEnPesos(cuenta.getSaldo() * cotizacion);
        } else {
            response.setSaldoEnPesos(cuenta.getSaldo());
            response.setCotizacionMepCompra(null);
        }

        return response;
    }

    public Cuenta crearCuenta(Cuenta cuenta) {
        return cuentaRepository.save(cuenta);
    }

    public Cuenta actualizarCuenta(Long id, Cuenta cuentaActualizada) {
        Cuenta existente = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException(id));
        existente.setNumeroCuenta(cuentaActualizada.getNumeroCuenta());
        existente.setSaldo(cuentaActualizada.getSaldo());
        existente.setMoneda(cuentaActualizada.getMoneda());
        return cuentaRepository.save(existente);
    }

    public void eliminarCuenta(Long id) {
        cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException(id));
        cuentaRepository.deleteById(id);
    }
}
