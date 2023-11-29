package com.thws.ar.stolpersteine.backend.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@ConfigurationProperties("backend.rest.security")
public class WebSecurityConfigurationProperties {

    @Setter
    private List<String> allowlist;
}
