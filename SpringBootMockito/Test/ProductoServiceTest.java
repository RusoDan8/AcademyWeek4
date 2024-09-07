package com.libreria.bdlibreria.service;
import com.libreria.bdlibreria.model.Producto;
import com.libreria.bdlibreria.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class ProductoServiceTest
{
    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializa los mocks
    }

    @Test
    void testCrearProducto() {
        Producto producto = new Producto();
        producto.setNombre("Producto 1");
        producto.setPrecio(100.0);

        // Simular el comportamiento del repositorio
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto productoCreado = productoService.crearProducto(producto);

        assertNotNull(productoCreado);
        assertEquals("Producto 1", productoCreado.getNombre());
        assertEquals(100.0, productoCreado.getPrecio());

        verify(productoRepository, times(1)).save(producto);
    }

    @Test
    void testObtenerProductoPorId() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");

        when(productoRepository.findById(1L)).thenReturn(producto);

        Producto productoObtenido = productoService.obtenerProductoPorId(1L);

        assertNotNull(productoObtenido);
        assertEquals(1L, productoObtenido.getId());
        assertEquals("Producto 1", productoObtenido.getNombre());

        verify(productoRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerTodosLosProductos() {
        Producto producto1 = new Producto();
        producto1.setNombre("Producto 1");
        Producto producto2 = new Producto();
        producto2.setNombre("Producto 2");

        List<Producto> productos = Arrays.asList(producto1, producto2);

        when(productoRepository.findAll()).thenReturn(productos);

        List<Producto> resultado = productoService.obtenerTodosLosProductos();

        assertEquals(2, resultado.size());
        assertEquals("Producto 1", resultado.get(0).getNombre());
        assertEquals("Producto 2", resultado.get(1).getNombre());

        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testActualizarProducto() {
        Producto productoExistente = new Producto();
        productoExistente.setId(1L);
        productoExistente.setNombre("Producto 1");
        productoExistente.setPrecio(100.0);

        when(productoRepository.findById(1L)).thenReturn(productoExistente);
        when(productoRepository.update(productoExistente)).thenReturn(productoExistente);

        productoExistente.setPrecio(150.0);

        Producto productoActualizado = productoService.actualizarProducto(productoExistente);

        assertNotNull(productoActualizado);
        assertEquals(150.0, productoActualizado.getPrecio());

        verify(productoRepository, times(1)).update(productoExistente);
    }

    @Test
    void testEliminarProducto() {
        Producto producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto 1");

        when(productoRepository.findById(1L)).thenReturn(producto);

        productoService.eliminarProducto(1L);

        verify(productoRepository, times(1)).delete(1L);
    }
}
