package com.crespect.city_weather.domain;

import lombok.Data;

@Data
public class CityWeather {

    private Long id;
    private Long cityId;
    private String cityName;
    private double temperature;
    private TemperatureScale temperatureScale;
    private String iconCode;

}
