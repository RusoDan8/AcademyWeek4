package com.libreria.bdlibreria.service;
import com.libreria.bdlibreria.model.Producto;
import com.libreria.bdlibreria.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductoService
{
    @Autowired
    private ProductoRepository productoRepository;

    public Producto crearProducto(Producto producto) {
        return productoRepository.save(producto);
    }

    public Producto obtenerProductoPorId(Long id) {
        return productoRepository.findById(id);
    }

    public List<Producto> obtenerTodosLosProductos() {
        return productoRepository.findAll();
    }

    public Producto actualizarProducto(Producto producto) {
        return productoRepository.update(producto);
    }

    public void eliminarProducto(Long id) {
        productoRepository.delete(id);
    }
}
