package com.Client.api;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaServiceTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private DolarService dolarService;

    @InjectMocks
    private CuentaService cuentaService;

    @Test
    void listarCuentas_devuelveListaCorrectamente() {
        CuentaAhorro cuenta = new CuentaAhorro();
        cuenta.setId(1L);
        cuenta.setSaldo(500.0);
        cuenta.setMoneda("ARS");
        cuenta.setNumeroCuenta("001-ARS");

        DolarResponse mepFalso = new DolarResponse();
        mepFalso.setCompra(1000.0);

        when(cuentaRepository.findAll()).thenReturn(List.of(cuenta));
        when(dolarService.obtenerDolarMep()).thenReturn(mepFalso);

        List<CuentaResponse> resultado = cuentaService.listarCuentas();

        assertEquals(1, resultado.size());
    }

    @Test
    void obtenerPorId_cuentaNoExiste_lanzaExcepcion() {
        when(cuentaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CuentaNoEncontradaException.class, () -> {
            cuentaService.obtenerPorId(99L);
        });
    }

    @Test
    void obtenerPorId_cuentaUSD_calculaSaldoEnPesos() {
        CuentaAhorro cuenta = new CuentaAhorro();
        cuenta.setId(1L);
        cuenta.setSaldo(100.0);
        cuenta.setMoneda("USD");
        cuenta.setNumeroCuenta("001-USD");

        DolarResponse mepFalso = new DolarResponse();
        mepFalso.setCompra(1000.0);

        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuenta));
        when(dolarService.obtenerDolarMep()).thenReturn(mepFalso);

        CuentaResponse response = cuentaService.obtenerPorId(1L);

        assertEquals(100000.0, response.getSaldoEnPesos());
        assertEquals(1000.0, response.getCotizacionMepCompra());
    }
}
