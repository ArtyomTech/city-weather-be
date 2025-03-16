package com.crespect.city_weather.repository;

import com.crespect.city_weather.entity.CityWeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityWeatherRepository extends JpaRepository<CityWeatherEntity, Long> { }
