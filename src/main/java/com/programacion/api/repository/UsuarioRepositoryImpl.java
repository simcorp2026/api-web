package com.programacion.api.repository;

import java.util.Optional;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import com.programacion.api.repository.modelo.Usuario;

@Transactional
@ApplicationScoped
public class UsuarioRepositoryImpl implements IUsuarioRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void insertar(Usuario usuario) {
        this.entityManager.persist(usuario);
    }

    @Override
    public Optional<Usuario> buscarPorUsername(String username) {
        return this.entityManager.createQuery("SELECT u FROM Usuario u WHERE u.username = :username", Usuario.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public long contar() {
        return this.entityManager.createQuery("SELECT COUNT(u) FROM Usuario u", Long.class).getSingleResult();
    }
}
