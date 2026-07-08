package com.tarjetas.api.tarjeta;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tarjeta {

    private static final int DIAS_VENCIMIENTO_ALERTA = 30;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;             // FK hacia la API de clientes

    private String numeroTarjeta;       // últimos 4 dígitos visibles

    @Enumerated(EnumType.STRING)
    private TipoTarjeta tipo;           // CREDITO, DEBITO, PREPAGA

    @Enumerated(EnumType.STRING)
    private EstadoTarjeta estado;       // ACTIVA, BLOQUEADA, CANCELADA, VENCIDA

    private String marca;               // VISA, MASTERCARD, AMEX, etc.

    private BigDecimal limiteCredito;   // solo aplica para CREDITO
    private BigDecimal saldoDisponible;
    private BigDecimal saldoUtilizado;

    private LocalDate fechaVencimiento; // fecha de expiración de la tarjeta
    private LocalDate fechaEmision;
    private LocalDateTime fechaAlta;
    private LocalDateTime fechaModificacion;
    private LocalDateTime fechaRenovacion;
    private LocalDateTime fechaNotificacion;


    public boolean estaProximaAVencer() {
        if (fechaVencimiento == null) return false;
        return !fechaVencimiento.isBefore(LocalDate.now()) &&
               fechaVencimiento.isBefore(LocalDate.now().plusDays(DIAS_VENCIMIENTO_ALERTA));
    }

    public boolean tieneDisponible(BigDecimal monto) {
        if (saldoDisponible == null) return false;
        return saldoDisponible.compareTo(monto) >= 0;
    }
}
