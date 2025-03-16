package com.crespect.city_weather.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record CityWeatherDto(
        CoordDto coord,
        List<WeatherDto> weather,
        String base,
        MainDto main,
        int visibility,
        WindDto wind,
        CloudsDto clouds,
        long dt,
        SysDto sys,
        int timezone,
        long id,
        String name,
        int cod
) {
    public record WeatherDto(int id, String main, String description, String icon) { }

    @Builder
    public record MainDto(
            double temp,
            double feelsLike,
            double tempMin,
            double tempMax,
            int pressure,
            int humidity,
            int seaLevel,
            int grndLevel
    ) { }

    public record WindDto(double speed, int deg, double gust) { }
    public record CloudsDto(int all) { }
    public record SysDto(int type, int id, String country, long sunrise, long sunset) { }
}
