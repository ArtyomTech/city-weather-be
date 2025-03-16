package com.crespect.city_weather.dto;

public record CityDto(
        int id,
        String name,
        String state,
        String country,
        CoordDto coord
) { }
