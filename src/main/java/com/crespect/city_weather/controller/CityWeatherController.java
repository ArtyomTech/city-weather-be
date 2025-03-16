package com.crespect.city_weather.controller;

import com.crespect.city_weather.domain.CityWeather;
import com.crespect.city_weather.service.CityWeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
public class CityWeatherController {

    private final CityWeatherService cityWeatherService;

    @GetMapping("/cities-weather")
    public ResponseEntity<List<CityWeather>> getCitiesWeather() {
        List<CityWeather> citiesWeather = cityWeatherService.getCitiesWeather();
        return ResponseEntity.ok(citiesWeather);
    }

}
