package com.programacion.api.repository.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {
    @Id
    @GeneratedValue(generator = "seq_detalle", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_detalle", sequenceName = "seq_detalle", allocationSize = 1)
    @Column(name = "deve_id")
    private Integer id;
    @Column(name = "deve_cantidad")
    private Double cantidad;
    @Column(name = "deve_precio_unitario")
    private Double precioUnitario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "vent_id", nullable = false)
    private Venta venta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "prod_id", nullable = false)
    private Producto producto;

    public DetalleVenta() {
    }

    public DetalleVenta(Integer id, Double cantidad, Double precioUnitario) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
