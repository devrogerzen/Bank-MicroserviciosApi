package com.tarjetas.api.producto;

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
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clienteId;             // FK hacia la API de clientes

    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;

    private String nombre;              // nombre comercial del producto
    private String descripcion;

    private BigDecimal montoAsociado;   // capital del préstamo, monto del plazo fijo, etc.
    private BigDecimal tasaInteres;     // TNA o TEA según producto

    private boolean activo;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private LocalDateTime fechaAlta;

    public boolean estaVigente() {
        if (fechaVencimiento == null) return activo;
        return activo && !LocalDate.now().isAfter(fechaVencimiento);
    }
}
