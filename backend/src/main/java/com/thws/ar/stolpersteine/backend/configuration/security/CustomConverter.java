package com.thws.ar.stolpersteine.backend.configuration.security;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomConverter implements Converter<Jwt, AbstractAuthenticationToken> {

    private final JwtReader jwtReader;

    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        var resourceMap = jwtReader.convertResourceMap(jwt);
        ArrayList<String> roles = jwtReader.readRoles(resourceMap);
        List<AppAuthorities> appAuthorities = new ArrayList<>();
        for (var role : roles) {
            if (AppRoles.ADMIN.getValue().equals(role) || AppRoles.USER.getValue().equals(role)) {
                appAuthorities.add(new AppAuthorities(role.toUpperCase()));
                log.debug("Has Role: " + role);
            }
        }
        return new JwtAuthenticationToken(jwt, appAuthorities);
    }
}
