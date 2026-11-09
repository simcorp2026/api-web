package com.programacion.api.service;

import java.util.List;
import java.util.function.Function;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import com.programacion.api.repository.IProductoRepository;
import com.programacion.api.repository.modelo.Producto;
import com.programacion.api.service.to.ProductoTo;

@ApplicationScoped
public class ProductoServiceImpl implements IProductoService {
    @Inject
    private IProductoRepository iProductoRepository;
    // public ProductoTo(Integer id, String codigoBarras, String categoria, Double
    // stock, Double precio) {
    private Function<Producto, ProductoTo> mapTo = p -> {
        ProductoTo pTo = new ProductoTo(p.getId(), p.getCodigoBarras(), p.getCategoria(), p.getStock(), p.getPrecio());
        return pTo;
    };
    // public Producto(Integer id, String codigoBarras, String categoria, Double
    // stock, Double precio) {
    private Function<ProductoTo, Producto> mapProducto = p -> {
        Producto pTo = new Producto(p.getId(), p.getCodigoBarras(), p.getCategoria(), p.getStock(), p.getPrecio());
        return pTo;
    };
    private Function<List<Producto>, List<ProductoTo>> mapList = p -> {
        List<ProductoTo> ml = p.stream().map(mapTo).toList();
        return ml;
    };

    /**
     * Sin id: inserta un producto nuevo. Con id: suma el stock al producto existente.
     * Es transaccional para que el cambio de stock se persista al terminar.
     */
    @Override
    @Transactional
    public void guardar(ProductoTo producto) {
        Integer id = producto.getId();
        if (id == null) {
            this.iProductoRepository.insertar(this.mapProducto.apply(producto));
            return;
        }
        Producto p = this.iProductoRepository.seleccionar(id);
        if (p == null || p.getId() == null) {
            throw new NegocioException(404, "Producto no encontrado: " + id);
        }
        p.setStock(p.getStock() + producto.getStock());
    }

    @Override
    public List<ProductoTo> buscarTodos() {
        return this.mapList.apply(this.iProductoRepository.seleccionarTodos());
    }

    @Override
    public ProductoTo buscar(Integer id) {
        try {
            return this.mapTo.apply(this.iProductoRepository.seleccionar(id));
        } catch (Exception e) {
            return new ProductoTo();
        }
    }

}
