package com.thws.ar.stolpersteine.backend.configuration.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppRoles {
    ADMIN("ADMIN"),
    USER("USER");
    private final String value;
}
