package com.tarjetas.api;

import com.tarjetas.api.tarjeta.EstadoTarjeta;
import com.tarjetas.api.tarjeta.Tarjeta;
import com.tarjetas.api.tarjeta.TarjetaRepository;
import com.tarjetas.api.tarjeta.TipoTarjeta;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class TarjetaRepositoryTest {

    @Autowired
    private TarjetaRepository tarjetaRepository;

    @Test
    void debeGuardarTarjeta() {
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setClienteId(1L);
        tarjeta.setNumeroTarjeta("4321");
        tarjeta.setTipo(TipoTarjeta.CREDITO);
        tarjeta.setEstado(EstadoTarjeta.ACTIVA);
        tarjeta.setMarca("VISA");
        tarjeta.setLimiteCredito(new BigDecimal("100000.00"));
        tarjeta.setSaldoDisponible(new BigDecimal("100000.00"));
        tarjeta.setSaldoUtilizado(BigDecimal.ZERO);
        tarjeta.setFechaEmision(LocalDate.now());
        tarjeta.setFechaVencimiento(LocalDate.now().plusYears(3));

        Tarjeta guardada = tarjetaRepository.save(tarjeta);

        assertThat(guardada.getId()).isNotNull();
        assertThat(guardada.getNumeroTarjeta()).isEqualTo("4321");
        assertThat(guardada.getTipo()).isEqualTo(TipoTarjeta.CREDITO);
    }
}
