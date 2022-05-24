package com.thang.forecastweather.model.weather7Days;

import java.io.Serializable;

import lombok.Data;

@Data
public class Main implements Serializable {

    private float temp_min;

    private float temp_max;

    private int humidity;
}
