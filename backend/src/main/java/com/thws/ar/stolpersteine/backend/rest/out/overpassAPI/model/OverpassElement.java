package com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class OverpassElement {
    private String type;
    private long id;
    private double lat;
    private double lon;
    private Map<String, String> tags;
}
