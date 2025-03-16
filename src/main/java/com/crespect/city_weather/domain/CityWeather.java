package com.crespect.city_weather.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityWeather {

    private Long id;
    private Long cityId;
    private String cityName;
    private double temperature;
    private TemperatureScale temperatureScale;
    private String iconCode;

}
