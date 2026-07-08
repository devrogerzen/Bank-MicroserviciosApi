package com.tarjetas.api.tarjeta;

public class TarjetaNotFoundException extends RuntimeException {
    public TarjetaNotFoundException(String message) {
        super(message);
    }
}
