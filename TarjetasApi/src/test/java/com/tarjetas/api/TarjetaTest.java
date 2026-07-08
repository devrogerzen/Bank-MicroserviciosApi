package com.tarjetas.api;

import com.tarjetas.api.tarjeta.Tarjeta;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class TarjetaTest {

    @Test
    void estaProximaAVencerTest() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setFechaVencimiento(LocalDate.now().plusDays(15));

        assertThat(tarjeta.estaProximaAVencer()).isTrue();
    }

    @Test
    void noEstaProximaAVencerTest() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setFechaVencimiento(LocalDate.now().plusYears(2));

        assertThat(tarjeta.estaProximaAVencer()).isFalse();
    }

    @Test
    void tieneDisponibleTest() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setSaldoDisponible(new BigDecimal("50000.00"));

        assertThat(tarjeta.tieneDisponible(new BigDecimal("10000.00"))).isTrue();
        assertThat(tarjeta.tieneDisponible(new BigDecimal("60000.00"))).isFalse();
    }
}
