package com.programacion.api.service;

import java.util.List;

import com.programacion.api.service.to.ProductoTo;

public interface IProductoService {
    public void guardar(ProductoTo producto);

    public List<ProductoTo> buscarTodos();

    public ProductoTo buscar(Integer id);
}
