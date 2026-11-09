package com.programacion.api.service.to;

public class ProductoTo {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String codigoBarras;
    private String categoria;
    private Double stock;
    private Double precio;

    public ProductoTo() {
    }

    public ProductoTo(Integer id, String codigoBarras, String categoria, Double stock, Double precio) {
        this.id = id;
        this.codigoBarras = codigoBarras;
        this.categoria = categoria;
        this.stock = stock;
        this.precio = precio;
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
