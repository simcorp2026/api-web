package com.programacion.api.service.to;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Línea de una venta. En la entrada solo se usan productoId y cantidad; el precio
 * unitario lo fija el servidor a partir del producto y se devuelve en la salida.
 */
public class DetalleVentaTo {
    @NotNull(message = "productoId es obligatorio")
    private Integer productoId;

    @NotNull(message = "cantidad es obligatoria")
    @Positive(message = "cantidad debe ser mayor que cero")
    private Double cantidad;

    private Double precioUnitario;

    public DetalleVentaTo() {
    }

    public DetalleVentaTo(Integer productoId, Double cantidad, Double precioUnitario) {
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
