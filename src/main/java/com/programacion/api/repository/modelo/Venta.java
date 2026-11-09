package com.programacion.api.repository.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "venta")
public class Venta {
    @Id
    @GeneratedValue(generator = "seq_venta", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_venta", sequenceName = "seq_venta", allocationSize = 1)
    @Column(name = "vent_id")
    private Integer id;
    @Column(name = "vent_numero_ventas")
    private Integer numeroVentas;
    @Column(name = "vent_cedula_cliente")
    private String cedulaCliente;
    @Column(name = "vent_total_venta")
    private Double totalVenta;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVenta> detalles = new ArrayList<>();

    public Venta() {
    }

    public Venta(Integer id, Integer numeroVentas, String cedulaCliente, Double totalVenta) {
        this.id = id;
        this.numeroVentas = numeroVentas;
        this.cedulaCliente = cedulaCliente;
        this.totalVenta = totalVenta;
    }

    /** Agrega un detalle y mantiene la referencia bidireccional. */
    public void agregarDetalle(DetalleVenta detalle) {
        detalle.setVenta(this);
        this.detalles.add(detalle);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumeroVentas() {
        return numeroVentas;
    }

    public void setNumeroVentas(Integer numeroVentas) {
        this.numeroVentas = numeroVentas;
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

    public List<DetalleVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleVenta> detalles) {
        this.detalles = detalles;
    }

}
