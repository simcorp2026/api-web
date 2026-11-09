package com.programacion.api.repository;

import java.util.List;

import com.programacion.api.repository.modelo.Producto;

public interface IProductoRepository {
    public void insertar(Producto producto);

    public List<Producto> seleccionarTodos();

    public Producto seleccionar(Integer id);

}
