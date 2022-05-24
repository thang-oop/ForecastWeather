package com.thang.forecastweather.model.weatherCurrent;

import java.util.List;

import lombok.Data;

@Data
public class WeatherCurrent {

    private String name;

    private List<Weather> weather;

    private Main main;

    private Wind wind;

    private long dt;
}
