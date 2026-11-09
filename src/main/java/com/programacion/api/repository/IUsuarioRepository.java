package com.programacion.api.repository;

import java.util.Optional;

import com.programacion.api.repository.modelo.Usuario;

public interface IUsuarioRepository {
    public void insertar(Usuario usuario);

    public Optional<Usuario> buscarPorUsername(String username);

    public long contar();
}
