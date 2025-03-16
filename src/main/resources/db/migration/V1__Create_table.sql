CREATE TABLE city_weather (
    id BIGSERIAL PRIMARY KEY,
    city_id BIGINT NOT NULL,
    city_name VARCHAR(255) NOT NULL,
    temperature DOUBLE PRECISION NOT NULL,
    temperature_scale CHAR(1) NOT NULL,
    icon_code VARCHAR(3) NOT NULL
);