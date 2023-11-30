package com.thws.ar.stolpersteine.backend.rest.in.publicAccess;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicRestAdapter {

    @GetMapping
    public String helloWorld() {
        return "Hello World Public";
    }
}
