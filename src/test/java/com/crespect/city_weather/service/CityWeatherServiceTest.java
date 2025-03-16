package com.crespect.city_weather.service;

import com.crespect.city_weather.config.ApiConfig;
import com.crespect.city_weather.dto.CityWeatherDto;
import com.crespect.city_weather.mapper.CityWeatherMapper;
import com.crespect.city_weather.mapper.CityWeatherMapperImpl;
import com.crespect.city_weather.repository.CityWeatherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CityWeatherServiceTest {

    private static final Long CITY_ID = 12345L;
    private static final String WEATHER_URL = "https://api.weather.com/weather";

    @Mock
    private CityWeatherRepository cityWeatherRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private ApiConfig apiConfig;

    @Spy
    private CityWeatherMapper cityWeatherMapper = new CityWeatherMapperImpl();

    @InjectMocks
    private CityWeatherService cityWeatherService;

    @Test
    void fetchCityWeather_receivedCityWeatherResponse() {
        mockApiConfig();

        String url = UriComponentsBuilder.fromUriString(WEATHER_URL)
                .queryParam("id", CITY_ID)
                .queryParam("appid", "dummy-api-key")
                .queryParam("units", "metric")
                .toUriString();
        CityWeatherDto mockResponse = CityWeatherDto
                .builder()
                .name("Tallinn")
                .main(CityWeatherDto.MainDto.builder().temp(20).build()).build();
        when(restTemplate.getForObject(url, CityWeatherDto.class)).thenReturn(mockResponse);

        CityWeatherDto result = cityWeatherService.fetchCityWeather(CITY_ID);

        assertNotNull(result);
        assertEquals(mockResponse.name(), result.name());
        assertNotNull(result.main());
        assertEquals(mockResponse.main().temp(), result.main().temp());

        verify(restTemplate).getForObject(url, CityWeatherDto.class);
    }

    @Test
    void fetchCityWeather_receivedEmptyResponse() {
        mockApiConfig();

        String url = UriComponentsBuilder.fromUriString(WEATHER_URL)
                .queryParam("id", CITY_ID)
                .queryParam("appid", "dummy-api-key")
                .queryParam("units", "metric")
                .toUriString();
        when(restTemplate.getForObject(url, CityWeatherDto.class)).thenReturn(null);

        CityWeatherDto result = cityWeatherService.fetchCityWeather(CITY_ID);

        assertNull(result);
        verify(restTemplate).getForObject(url, CityWeatherDto.class);
    }

    private void mockApiConfig() {
        when(apiConfig.getWeatherUrl()).thenReturn(WEATHER_URL);
        when(apiConfig.getKey()).thenReturn("dummy-api-key");
        when(apiConfig.getUnits()).thenReturn("metric");
    }

}
