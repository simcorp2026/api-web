package com.programacion.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programacion.api.repository.IProductoRepository;
import com.programacion.api.repository.modelo.Producto;
import com.programacion.api.service.to.ProductoTo;

@ExtendWith(MockitoExtension.class)
class ProductoServiceImplTest {

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private ProductoServiceImpl service;

    @Test
    void guardarSinIdInsertaProductoNuevo() {
        ProductoTo entrada = new ProductoTo(null, "COD-1", "Bebidas", 5.0, 1.5);

        service.guardar(entrada);

        verify(productoRepository).insertar(any(Producto.class));
    }

    @Test
    void guardarConIdSumaStockAlProductoExistente() {
        Producto existente = new Producto(1, "COD-1", "Bebidas", 4.0, 1.5);
        when(productoRepository.seleccionar(1)).thenReturn(existente);

        service.guardar(new ProductoTo(1, "COD-1", "Bebidas", 3.0, 1.5));

        assertEquals(7.0, existente.getStock(), 0.001);
        verify(productoRepository, never()).insertar(any());
    }

    @Test
    void guardarConIdInexistenteLanza404() {
        when(productoRepository.seleccionar(99)).thenReturn(null);

        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.guardar(new ProductoTo(99, "COD-9", "X", 1.0, 1.0)));

        assertEquals(404, ex.getCodigoHttp());
        verify(productoRepository, never()).insertar(any());
    }
}
