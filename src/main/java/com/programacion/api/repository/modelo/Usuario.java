package com.programacion.api.repository.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(generator = "seq_usuario", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "seq_usuario", sequenceName = "seq_usuario", allocationSize = 1)
    @Column(name = "usu_id")
    private Integer id;
    @Column(name = "usu_username", nullable = false, unique = true, length = 50)
    private String username;
    @Column(name = "usu_password_hash", nullable = false)
    private String passwordHash;
    @Column(name = "usu_rol", nullable = false, length = 20)
    private String rol;

    public Usuario() {
    }

    public Usuario(String username, String passwordHash, String rol) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.rol = rol;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getRol() {
        return rol;
    }
}
