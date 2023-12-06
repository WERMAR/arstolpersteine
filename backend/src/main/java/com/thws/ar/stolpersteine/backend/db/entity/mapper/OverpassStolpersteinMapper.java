package com.thws.ar.stolpersteine.backend.db.entity.mapper;

import com.thws.ar.stolpersteine.backend.db.entity.OverpassStolperstein;
import com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model.OverpassElement;
import com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model.Tags;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class OverpassStolpersteinMapper {

    public OverpassStolperstein toEntity(OverpassElement element) {
        return OverpassStolperstein.builder()
                .id(element.getId())
                .lat((float) element.getLat())
                .lng((float) element.getLon())
                .city(element.getTags().get(Tags.ADDR_CITY.getValue()))
                .country(element.getTags().get(Tags.ADDR_COUNTRY.getValue()))
                .street(element.getTags().get(Tags.ADDR_STREET.getValue()))
                .suburb(element.getTags().get(Tags.ADDR_SUBURB.getValue()))
                .housenumber(element.getTags().get(Tags.ADDR_HOUSENUMBER.getValue()))
                .postcode(element.getTags().get(Tags.ADDR_POSTCODE.getValue()))
                .memorialText(element.getTags().get(Tags.MEMORIAL_TEXT.getValue()))
                .victimName(element.getTags().get(Tags.VICTIM_NAME.getValue()))
                .dateOfBirth(element.getTags().get(Tags.PERSON_DATE_OF_BIRTH.getValue()))
                .dateOfDeath(element.getTags().get(Tags.PERSON_DATE_OF_DEATH.getValue()))
                .build();
    }
}
