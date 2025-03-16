package com.crespect.city_weather.service;

import com.crespect.city_weather.config.ApiConfig;
import com.crespect.city_weather.dto.CityDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Service
@RequiredArgsConstructor
public class CityService {

    private final ApiConfig apiConfig;
    private final ObjectMapper objectMapper;

    @SneakyThrows
    public List<CityDto> fetchCities() {
        URI uri = URI.create(apiConfig.getCitiesUrl());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(uri).build();
        HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(response.body())) {
            List<CityDto> cities = objectMapper.readValue(gzipInputStream,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, CityDto.class));
            return cities.stream()
                    .filter(city -> apiConfig.getCityNames().contains(city.name()))
                    .collect(Collectors.collectingAndThen(
                            Collectors.toMap(
                                    CityDto::name,
                                    Function.identity(),
                                    (existing, replacement) -> existing
                            ),
                            map -> new ArrayList<>(map.values())
                    ));
        }
    }

}

