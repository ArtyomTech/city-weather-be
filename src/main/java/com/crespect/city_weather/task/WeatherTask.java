package com.crespect.city_weather.task;

import com.crespect.city_weather.config.ApiConfig;
import com.crespect.city_weather.domain.TemperatureScale;
import com.crespect.city_weather.dto.CityDto;
import com.crespect.city_weather.dto.CityWeatherDto;
import com.crespect.city_weather.entity.CityWeatherEntity;
import com.crespect.city_weather.mapper.CityWeatherMapper;
import com.crespect.city_weather.repository.CityWeatherRepository;
import com.crespect.city_weather.service.CityService;
import com.crespect.city_weather.service.CityWeatherService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherTask {

    private final CityService cityService;
    private final CityWeatherService cityWeatherService;
    private final CityWeatherRepository cityWeatherRepository;
    private final CityWeatherMapper cityWeatherMapper;
    private final ApiConfig apiConfig;

    @PostConstruct
    public void loadWeatherDataOnStartup() {
        log.info("Loading weather data");
        loadWeatherData();
    }

    @Scheduled(cron = "${task.update-weather.cron}")
    @Retryable(retryFor = Exception.class, maxAttempts = 5, backoff = @Backoff(delay = 5000))
    public void updateWeatherDataOnSchedule() {
        log.info("Updating weather data");
        updateWeatherData();
    }

    @Transactional
    public void loadWeatherData() {
        List<CityDto> cities = cityService.fetchCities();
        List<CityWeatherEntity> citiesWeather = cities.stream()
                .map(city -> {
                    CityWeatherDto cityWeather = cityWeatherService.fetchCityWeather(city.id());
                    CityWeatherEntity cityWeatherEntity = cityWeatherMapper.mapToEntity(cityWeather);
                    cityWeatherEntity.setTemperatureScale(TemperatureScale.fromUnits(apiConfig.getUnits()));
                    return cityWeatherEntity;
                })
                .toList();
        cityWeatherRepository.deleteAll();
        cityWeatherRepository.saveAll(citiesWeather);
        log.info("Weather data loaded successfully");
    }

    @Transactional
    public void updateWeatherData() {
        List<CityWeatherEntity> existingCitiesWeather = cityWeatherRepository.findAll();
        existingCitiesWeather.forEach(existingCityWeather -> {
            CityWeatherDto newCityWeather = cityWeatherService.fetchCityWeather(existingCityWeather.getCityId());
            CityWeatherEntity updatedCityWeather = cityWeatherMapper.mapToEntity(newCityWeather);
            existingCityWeather.setTemperature(updatedCityWeather.getTemperature());
            existingCityWeather.setIconCode(updatedCityWeather.getIconCode());
        });
        cityWeatherRepository.saveAll(existingCitiesWeather);
        log.info("Weather data updated successfully");
    }

}
