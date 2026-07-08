package com.tarjetas.api.dolar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dolar {

    private String moneda;
    private String casa;
    private String nombre;
    private double compra;
    private double venta;
    private LocalDate fechaActualizacion;
}
