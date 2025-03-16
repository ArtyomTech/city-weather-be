package com.crespect.city_weather.mapper;

import com.crespect.city_weather.domain.CityWeather;
import com.crespect.city_weather.dto.CityWeatherDto;
import com.crespect.city_weather.entity.CityWeatherEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CityWeatherMapper {

    List<CityWeather> mapToDomainList(List<CityWeatherEntity> cityWeatherEntityList);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "temperatureScale", ignore = true)
    @Mapping(target = "cityId", source = "id")
    @Mapping(target = "cityName", source = "name")
    @Mapping(target = "temperature", source = "main.temp")
    @Mapping(target = "iconCode", source = "weather.first.icon")
    CityWeatherEntity mapToEntity(CityWeatherDto cityWeatherDto);

}
