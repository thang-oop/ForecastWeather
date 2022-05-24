package com.thang.forecastweather.model.weather7Days;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class Weather7Day implements Serializable {

    private City city;

    private List<ListForecast> list;
}
