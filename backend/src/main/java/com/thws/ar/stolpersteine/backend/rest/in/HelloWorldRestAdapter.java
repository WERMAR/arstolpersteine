package com.thws.ar.stolpersteine.backend.rest.in;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
@RequestMapping("/api/hello")
public class HelloWorldRestAdapter {

    @GetMapping
    @ResponseBody
    @Secured("ADMIN")
    public String getAllExistingBranches() {
        return "Hello World";
    }
}
