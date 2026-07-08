package com.Client.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DolarResponse {

    private String moneda;
    private String casa;
    private String nombre;
    private Double compra;
    private Double venta;

    @JsonProperty("fechaActualizacion")
    private String fechaActualizacion;
}
