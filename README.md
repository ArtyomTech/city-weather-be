# City Weather BE Application

This document provides instructions on how to start the Spring Boot application, which connects to a PostgreSQL database running in a Docker container.

## Prerequisites

1. **Maven** and **JDK 21+** are required to build and run the application.

2. **Docker**: Ensure you have Docker installed and running on your machine.

## Startup Instructions

### Step 1: Start the PostgreSQL Container

Before starting the Spring Boot application, you need to run the PostgreSQL container. This can be done using Docker. Follow these steps:

1. **Navigate to the project directory** where the `docker-compose.yml` file is located.

2. **Run Docker Compose** to start the PostgreSQL container:

    ```bash
    docker-compose up -d postgres
    ```

   This command will pull the latest `postgres` image and start a container named `postgres_container`. It will be accessible on port `5432` and will use the following environment variables:

    - `SPRING_PROFILES_ACTIVE: local` – Configures the Spring Boot application to use the `local` profile.
    - `POSTGRES_USER: postgres` – Sets the PostgreSQL username.
    - `POSTGRES_PASSWORD: postgres` – Sets the PostgreSQL password.
    - `POSTGRES_DB: city_weather` – Creates a database named `city_weather` for your application.

   The PostgreSQL container will store its data in a Docker volume (`postgres_data`), ensuring persistence across container restarts.

### Step 2: Run the Spring Boot Application

Once the PostgreSQL container is running, you can start the Spring Boot application.

1. **Navigate to the project directory** where the `pom.xml` file is located.

2. **Run the Spring Boot application** using Maven:

    ```bash
    ./mvnw spring-boot:run
    ```

   This will start the Spring Boot application on the default port `8080`.

### Step 3: Verify the Application

Once both the PostgreSQL container and the Spring Boot application are running:

- Access the application via `http://localhost:8080/cities-weather` in your web browser.
- Verify that the Spring Boot application successfully connects to the PostgreSQL database and loads or updates the data.

## Stopping the Application

1. To stop the PostgreSQL container:

    ```bash
    docker-compose down
    ```

2. To stop the Spring Boot application, press `Ctrl+C` in the terminal where it is running, or kill the Java process.

## Configuration

The application uses `application.yml` for the following configuration settings:

| **Configuration**          | **Description**                                                                                             |
|----------------------------|-------------------------------------------------------------------------------------------------------------|
| `task.update-weather.cron` | Cron expression for updating weather data. The current setting runs once a day at midnight (`0 0 0 * * ?`). |
| `api.key`                  | Your unique API key for authenticating with the OpenWeatherMap API.                                         |
| `api.weather-url`          | Base URL for the weather API (`https://api.openweathermap.org/data/2.5/weather`).                           |
| `api.units`                | Specifies the unit for temperature. Set to `metric` for Celsius.                                            |
| `api.cities-url`           | URL for downloading the list of cities (`https://bulk.openweathermap.org/sample/city.list.min.json.gz`).    |
| `api.city-names`           | List of city names for which weather data will be fetched.                                                  |