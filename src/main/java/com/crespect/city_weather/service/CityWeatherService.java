package com.crespect.city_weather.service;

import com.crespect.city_weather.config.ApiConfig;
import com.crespect.city_weather.domain.CityWeather;
import com.crespect.city_weather.dto.CityWeatherDto;
import com.crespect.city_weather.mapper.CityWeatherMapper;
import com.crespect.city_weather.repository.CityWeatherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityWeatherService {

    private final CityWeatherRepository cityWeatherRepository;
    private final CityWeatherMapper cityWeatherMapper;
    private final RestTemplate restTemplate;
    private final ApiConfig apiConfig;

    public CityWeatherDto fetchCityWeather(long id) {
        String url = UriComponentsBuilder.fromUriString(apiConfig.getWeatherUrl())
                .queryParam("id", id)
                .queryParam("appid", apiConfig.getKey())
                .queryParam("units", apiConfig.getUnits() != null ? apiConfig.getUnits() : null)
                .toUriString();
        return restTemplate.getForObject(url, CityWeatherDto.class);
    }

    public List<CityWeather> getCitiesWeather() {
        return cityWeatherMapper.mapToDomainList(cityWeatherRepository.findAll());
    }

}
