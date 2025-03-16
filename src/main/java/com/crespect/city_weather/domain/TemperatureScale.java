package com.crespect.city_weather.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TemperatureScale {
    F("imperial"),
    C("metric"),
    K(null);

    private final String units;

    public static TemperatureScale fromUnits(String units) {
        if (units == null) return K;
        return switch (units) {
            case "metric" -> C;
            case "imperial" -> F;
            default -> K;
        };
    }

}
