package com.tarjetas.api.tarjeta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TarjetaRepository extends JpaRepository<Tarjeta, Long> {

    List<Tarjeta> findByClienteId(Long clienteId);

    List<Tarjeta> findByEstado(EstadoTarjeta estado);
}
