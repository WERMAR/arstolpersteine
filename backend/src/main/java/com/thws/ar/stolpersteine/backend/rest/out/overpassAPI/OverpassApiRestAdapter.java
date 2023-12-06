package com.thws.ar.stolpersteine.backend.rest.out.overpassAPI;


import com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model.OverpassElement;
import com.thws.ar.stolpersteine.backend.rest.out.overpassAPI.model.OverpassModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OverpassApiRestAdapter {

    private static final String OVERPASS_URI = "interpreter?data=[out:json];nwr[\"memorial:type\"=\"stolperstein\"];out;";

    private final WebClient webClient;

    public List<OverpassElement> receiveStolpersteine() {
        var result = webClient
                .get()
                .uri(OVERPASS_URI)
                .retrieve()
                .bodyToMono(OverpassModel.class)
                .block(Duration.of(10, ChronoUnit.SECONDS));
        return Objects.requireNonNull(result).getElements();
    }
}
