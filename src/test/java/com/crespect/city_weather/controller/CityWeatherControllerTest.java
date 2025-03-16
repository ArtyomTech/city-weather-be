package com.crespect.city_weather.controller;

import com.crespect.city_weather.domain.CityWeather;
import com.crespect.city_weather.domain.TemperatureScale;
import com.crespect.city_weather.service.CityWeatherService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CityWeatherControllerTest {

    @Mock
    private CityWeatherService cityWeatherService;

    @InjectMocks
    private CityWeatherController cityWeatherController;

    @Test
    @SneakyThrows
    void getCitiesWeather_receivedCitiesWeatherResponse() {
        CityWeather city1 = CityWeather.builder()
                .id(1L)
                .cityId(101L)
                .cityName("New York")
                .temperature(25.5)
                .temperatureScale(TemperatureScale.C)
                .iconCode("01d")
                .build();
        CityWeather city2 = CityWeather.builder()
                .id(2L)
                .cityId(102L)
                .cityName("Los Angeles")
                .temperature(22.3)
                .temperatureScale(TemperatureScale.C)
                .iconCode("02d")
                .build();
        List<CityWeather> mockCitiesWeather = List.of(city1, city2);
        when(cityWeatherService.getCitiesWeather()).thenReturn(mockCitiesWeather);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(cityWeatherController).build();

        mockMvc.perform(get("/cities-weather")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(mockCitiesWeather.size()))
                .andExpect(jsonPath("$[0].cityName").value(city1.getCityName()))
                .andExpect(jsonPath("$[0].temperature").value(city1.getTemperature()))
                .andExpect(jsonPath("$[1].cityName").value(city2.getCityName()))
                .andExpect(jsonPath("$[1].temperature").value(city2.getTemperature()));

        verify(cityWeatherService).getCitiesWeather();
    }

}

