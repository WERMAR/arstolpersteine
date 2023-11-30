package com.thws.ar.stolpersteine.backend.configuration.security;

import com.nimbusds.jose.shaded.gson.internal.LinkedTreeMap;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtReader {

    public LinkedTreeMap<String, LinkedTreeMap<String, ArrayList<String>>> convertResourceMap(Jwt jwt) {
        var objMap = jwt.getClaim("resource_access");
        if (objMap instanceof LinkedTreeMap<?, ?>) {
            return (LinkedTreeMap<String, LinkedTreeMap<String, ArrayList<String>>>) objMap;
        }
        throw new JwtValidationException("JWT was not valid and cannot be read ", List.of(new OAuth2Error("500")));
    }

    public ArrayList<String> readRoles(LinkedTreeMap<String, LinkedTreeMap<String, ArrayList<String>>> map) {
        var list = map.get("backend-client").get("roles");
        if (list != null)
            return list;
        throw new JwtValidationException("JWT was not valid and cannot be read ", List.of(new OAuth2Error("500")));
    }
}
