package com.thws.ar.stolpersteine.backend.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public class AppAuthorities implements GrantedAuthority {

    private final String permission;

    @Override
    public String getAuthority() {
        return this.permission;
    }
}
