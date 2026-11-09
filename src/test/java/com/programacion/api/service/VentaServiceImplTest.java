package com.programacion.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programacion.api.repository.IProductoRepository;
import com.programacion.api.repository.IVentaRepository;
import com.programacion.api.repository.modelo.Producto;
import com.programacion.api.repository.modelo.Venta;
import com.programacion.api.service.to.DetalleVentaTo;
import com.programacion.api.service.to.RegistrarVentaTo;
import com.programacion.api.service.to.VentaTo;

@ExtendWith(MockitoExtension.class)
class VentaServiceImplTest {

    private static final String CEDULA = "0102030405";

    @Mock
    private IVentaRepository ventaRepository;

    @Mock
    private IProductoRepository productoRepository;

    @InjectMocks
    private VentaServiceImpl service;

    @Test
    void registrarCalculaTotalConIvaYDescuentaStock() {
        Producto producto = producto(1, 10.0, 5.0);
        when(productoRepository.seleccionar(1)).thenReturn(producto);

        VentaTo resultado = service.registrar(entrada(detalle(1, 2.0)));

        // subtotal 2 x 10 = 20; con IVA 15% = 23.00
        assertEquals(23.0, resultado.getTotalVenta(), 0.001);
        assertEquals(3.0, producto.getStock(), 0.001);

        ArgumentCaptor<Venta> captor = ArgumentCaptor.forClass(Venta.class);
        verify(ventaRepository).insertar(captor.capture());
        assertEquals(CEDULA, captor.getValue().getCedulaCliente());
        assertEquals(1, captor.getValue().getDetalles().size());
        assertEquals(10.0, captor.getValue().getDetalles().get(0).getPrecioUnitario(), 0.001);
    }

    @Test
    void productoInexistenteLanza404SinGuardarVenta() {
        when(productoRepository.seleccionar(99)).thenReturn(null);

        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.registrar(entrada(detalle(99, 1.0))));

        assertEquals(404, ex.getCodigoHttp());
        verify(ventaRepository, never()).insertar(any());
    }

    @Test
    void stockInsuficienteLanza409SinModificarStock() {
        Producto producto = producto(1, 10.0, 1.0);
        when(productoRepository.seleccionar(1)).thenReturn(producto);

        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.registrar(entrada(detalle(1, 2.0))));

        assertEquals(409, ex.getCodigoHttp());
        assertEquals(1.0, producto.getStock(), 0.001);
        verify(ventaRepository, never()).insertar(any());
    }

    @Test
    void productoRepetidoLanza400() {
        NegocioException ex = assertThrows(NegocioException.class,
                () -> service.registrar(entrada(detalle(1, 1.0), detalle(1, 2.0))));

        assertEquals(400, ex.getCodigoHttp());
        verify(productoRepository, never()).seleccionar(any());
        verify(ventaRepository, never()).insertar(any());
    }

    @Test
    void buscarVentaInexistenteLanza404() {
        when(ventaRepository.seleccionar(7)).thenReturn(Optional.empty());

        NegocioException ex = assertThrows(NegocioException.class, () -> service.buscar(7));

        assertEquals(404, ex.getCodigoHttp());
    }

    @Test
    void buscarTodasMapeaLasVentas() {
        Venta venta = new Venta(5, 1, CEDULA, 23.0);
        when(ventaRepository.seleccionarTodas()).thenReturn(List.of(venta));

        List<VentaTo> resultado = service.buscarTodas();

        assertEquals(1, resultado.size());
        assertEquals(5, resultado.get(0).getId());
        assertEquals(23.0, resultado.get(0).getTotalVenta(), 0.001);
    }

    private static Producto producto(Integer id, Double precio, Double stock) {
        return new Producto(id, "COD-" + id, "Categoria", stock, precio);
    }

    private static DetalleVentaTo detalle(Integer productoId, Double cantidad) {
        return new DetalleVentaTo(productoId, cantidad, null);
    }

    private static RegistrarVentaTo entrada(DetalleVentaTo... detalles) {
        RegistrarVentaTo entrada = new RegistrarVentaTo();
        entrada.setCedulaCliente(CEDULA);
        entrada.setDetalles(List.of(detalles));
        return entrada;
    }
}
