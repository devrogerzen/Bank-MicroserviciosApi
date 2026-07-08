package com.Client.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CuentaResponse {

    private Long id;
    private String numeroCuenta;
    private Double saldo;
    private String moneda;
    private Double saldoEnPesos;
    private Double cotizacionMepCompra;
}
