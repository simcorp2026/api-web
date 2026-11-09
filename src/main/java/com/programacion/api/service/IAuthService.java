package com.programacion.api.service;

import com.programacion.api.service.to.TokenTo;

public interface IAuthService {
    public TokenTo login(String username, String password);
}
