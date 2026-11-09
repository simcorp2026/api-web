package com.programacion.api.repository.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(generator = "seq_producto", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_producto", sequenceName = "seq_producto", allocationSize = 1)
    @Column(name = "prod_id")
    private Integer id;
    @Column(name = "prod_codigo_barras")
    private String codigoBarras;
    @Column(name = "prod_categoria")
    private String categoria;
    @Column(name = "prod_stock")
    private Double stock;
    @Column(name = "prod_precio")
    private Double precio;

    public Producto() {
    }

    public Producto(Integer id, String codigoBarras, String categoria, Double stock, Double precio) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Double getStock() {
        return stock;
    }

    public void setStock(Double stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

}
