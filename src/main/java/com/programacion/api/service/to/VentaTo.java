package com.programacion.api.service.to;

import java.util.List;

/** Representación de salida de una venta. */
public class VentaTo {
    private Integer id;
    private String cedulaCliente;
    private Double totalVenta;
    private List<DetalleVentaTo> detalles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedulaCliente() {
        return cedulaCliente;
    }

    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }

    public Double getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Double totalVenta) {
        this.totalVenta = totalVenta;
    }

    public List<DetalleVentaTo> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVentaTo> detalles) {
        this.detalles = detalles;
    }
}
