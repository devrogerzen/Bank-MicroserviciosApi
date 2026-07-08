package com.tarjetas.api.producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public List<Producto> getProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> getProductoById(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> getProductosByCliente(Long clienteId) {
        return productoRepository.findByClienteId(clienteId);
    }

    public List<Producto> getProductosByTipo(TipoProducto tipo) {
        return productoRepository.findByTipo(tipo);
    }

    public List<Producto> getProductosActivos() {
        return productoRepository.findByActivo(true);
    }

    public Producto addProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto updateProducto(Long id, Producto datosActualizados) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + id));
        producto.setNombre(datosActualizados.getNombre());
        producto.setDescripcion(datosActualizados.getDescripcion());
        producto.setMontoAsociado(datosActualizados.getMontoAsociado());
        producto.setTasaInteres(datosActualizados.getTasaInteres());
        producto.setActivo(datosActualizados.isActivo());
        producto.setFechaVencimiento(datosActualizados.getFechaVencimiento());
        return productoRepository.save(producto);
    }

    public void deleteById(Long id) {
        productoRepository.deleteById(id);
    }
}
