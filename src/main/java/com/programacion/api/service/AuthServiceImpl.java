package com.programacion.api.service;

import java.util.Optional;
import java.util.Set;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import com.programacion.api.repository.IUsuarioRepository;
import com.programacion.api.repository.modelo.Usuario;
import com.programacion.api.service.to.TokenTo;

@ApplicationScoped
public class AuthServiceImpl implements IAuthService {

    @Inject
    private IUsuarioRepository iUsuarioRepository;

    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @ConfigProperty(name = "smallrye.jwt.new-token.lifespan", defaultValue = "3600")
    long lifespan;

    /**
     * Valida credenciales y devuelve un JWT firmado con el rol del usuario como grupo.
     * Usuario inexistente y contraseña incorrecta devuelven el mismo error.
     */
    @Override
    public TokenTo login(String username, String password) {
        Optional<Usuario> usuario = this.iUsuarioRepository.buscarPorUsername(username);
        if (usuario.isEmpty() || !BcryptUtil.matches(password, usuario.get().getPasswordHash())) {
            throw new NegocioException(401, "Credenciales inválidas");
        }
        String token = Jwt.issuer(issuer)
                .upn(username)
                .groups(Set.of(usuario.get().getRol()))
                .expiresIn(lifespan)
                .sign();
        return new TokenTo(token, lifespan);
    }
}
