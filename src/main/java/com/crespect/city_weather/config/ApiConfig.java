package com.crespect.city_weather.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "api")
public class ApiConfig {

    private String key;
    private String weatherUrl;
    private String units;
    private String citiesUrl;
    private List<String> cityNames;

}

