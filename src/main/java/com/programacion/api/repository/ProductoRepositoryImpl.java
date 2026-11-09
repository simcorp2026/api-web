package com.programacion.api.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import com.programacion.api.repository.modelo.Producto;

@Transactional
@ApplicationScoped
public class ProductoRepositoryImpl implements IProductoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertar(Producto producto) {
        this.entityManager.persist(producto);
    }

    @Override
    public List<Producto> seleccionarTodos() {
        TypedQuery<Producto> myQ = this.entityManager.createQuery("SELECT p FROM Producto p", Producto.class);
        return myQ.getResultList();
    }

    @Override
    public Producto seleccionar(Integer id) {
        try {
         
        return this.entityManager.find(Producto.class, id);   
        } catch (Exception e) {
            return new Producto();
        }
    }

}
