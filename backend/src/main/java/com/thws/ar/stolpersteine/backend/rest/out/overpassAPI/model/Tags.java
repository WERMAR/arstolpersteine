package com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Tags {
    ADDR_CITY("addr:city"),
    ADDR_COUNTRY("addr:country"),
    ADDR_HOUSENUMBER("addr:housenumber"),
    ADDR_POSTCODE("addr:postcode"),
    ADDR_STREET("addr:street"),
    ADDR_SUBURB("addr:suburb"),
    DESCRIPTION_DE("description:de"),
    FIXME_WIKIPEDIA("fixme:wikipedia"),
    HISTORIC("historic"),
    MEMORIAL("memorial"),
    MEMORIAL_ADDR("memorial:addr"),
    MEMORIAL_TEXT("memorial:text"),
    MEMORIAL_TYPE("memorial:type"),
    NAME("name"),
    NETWORK("network"),
    PERSON_DATE_OF_BIRTH("person:date_of_birth"),
    PERSON_DATE_OF_DEATH("person:date_of_death"),
    WIKIMEDIA_COMMONS("wikimedia_commons"),
    WIKIPEDIA("wikipedia"),
    VICTIM_NAME("name");
    private final String value;
}