package com.tarjetas.api.producto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findByClienteId(Long clienteId);

    List<Producto> findByTipo(TipoProducto tipo);

    List<Producto> findByActivo(boolean activo);
}
