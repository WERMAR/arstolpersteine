package com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OverpassModel {

    private String version;
    private String generator;
    private Object osm3s;
    private List<OverpassElement> elements;
}
