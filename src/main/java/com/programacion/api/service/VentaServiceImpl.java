package com.programacion.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.programacion.api.repository.IProductoRepository;
import com.programacion.api.repository.IVentaRepository;
import com.programacion.api.repository.modelo.DetalleVenta;
import com.programacion.api.repository.modelo.Producto;
import com.programacion.api.repository.modelo.Venta;
import com.programacion.api.service.to.DetalleVentaTo;
import com.programacion.api.service.to.RegistrarVentaTo;
import com.programacion.api.service.to.VentaTo;

@ApplicationScoped
public class VentaServiceImpl implements IVentaService {

    static final BigDecimal TASA_IVA = new BigDecimal("0.15");
    static final int BAD_REQUEST = 400;
    static final int NOT_FOUND = 404;
    static final int CONFLICT = 409;

    @Inject
    private IVentaRepository iVentaRepository;

    @Inject
    private IProductoRepository iProductoRepository;

    /**
     * Registra la venta, descuenta el stock y calcula el total con IVA.
     * Es transaccional: si alguna línea falla, no queda ninguna venta ni descuento a medias.
     */
    @Override
    @Transactional
    public VentaTo registrar(RegistrarVentaTo entrada) {
        validarSinProductosRepetidos(entrada.getDetalles());

        Venta venta = new Venta();
        venta.setCedulaCliente(entrada.getCedulaCliente());
        BigDecimal subtotal = BigDecimal.ZERO;

        for (DetalleVentaTo item : entrada.getDetalles()) {
            Producto producto = buscarProducto(item.getProductoId());
            double cantidad = item.getCantidad();

            if (producto.getPrecio() == null) {
                throw new NegocioException(CONFLICT, "El producto " + producto.getId() + " no tiene precio");
            }
            if (producto.getStock() == null || producto.getStock() < cantidad) {
                throw new NegocioException(CONFLICT, "Stock insuficiente para el producto " + producto.getId());
            }

            producto.setStock(producto.getStock() - cantidad);
            DetalleVenta detalle = new DetalleVenta(null, cantidad, producto.getPrecio());
            detalle.setProducto(producto);
            venta.agregarDetalle(detalle);

            subtotal = subtotal.add(BigDecimal.valueOf(producto.getPrecio()).multiply(BigDecimal.valueOf(cantidad)));
        }

        BigDecimal total = subtotal.multiply(BigDecimal.ONE.add(TASA_IVA)).setScale(2, RoundingMode.HALF_UP);
        venta.setTotalVenta(total.doubleValue());
        venta.setNumeroVentas(venta.getDetalles().size());

        this.iVentaRepository.insertar(venta);
        return mapear(venta);
    }

    @Override
    public List<VentaTo> buscarTodas() {
        return this.iVentaRepository.seleccionarTodas().stream().map(this::mapear).toList();
    }

    @Override
    public VentaTo buscar(Integer id) {
        return this.iVentaRepository.seleccionar(id)
                .map(this::mapear)
                .orElseThrow(() -> new NegocioException(NOT_FOUND, "Venta no encontrada: " + id));
    }

    private void validarSinProductosRepetidos(List<DetalleVentaTo> detalles) {
        Set<Integer> vistos = new HashSet<>();
        for (DetalleVentaTo item : detalles) {
            if (!vistos.add(item.getProductoId())) {
                throw new NegocioException(BAD_REQUEST, "Producto repetido en la venta: " + item.getProductoId());
            }
        }
    }

    private Producto buscarProducto(Integer id) {
        if (id == null) {
            throw new NegocioException(BAD_REQUEST, "productoId es obligatorio");
        }
        Producto producto = this.iProductoRepository.seleccionar(id);
        if (producto == null || producto.getId() == null) {
            throw new NegocioException(NOT_FOUND, "Producto no encontrado: " + id);
        }
        return producto;
    }

    private VentaTo mapear(Venta venta) {
        VentaTo to = new VentaTo();
        to.setId(venta.getId());
        to.setCedulaCliente(venta.getCedulaCliente());
        to.setTotalVenta(venta.getTotalVenta());
        to.setDetalles(venta.getDetalles().stream()
                .map(d -> new DetalleVentaTo(d.getProducto().getId(), d.getCantidad(), d.getPrecioUnitario()))
                .toList());
        return to;
    }
}
