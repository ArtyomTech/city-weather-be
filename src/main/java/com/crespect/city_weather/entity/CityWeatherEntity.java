package com.crespect.city_weather.entity;

import com.crespect.city_weather.domain.TemperatureScale;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "city_weather")
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CityWeatherEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long cityId;
    private String cityName;
    private double temperature;

    @Enumerated(EnumType.STRING)
    @Column(length = 1, nullable = false)
    private TemperatureScale temperatureScale;

    @Column(length = 3)
    private String iconCode;

}
