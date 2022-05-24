package com.thang.forecastweather.model.weather7Days;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ListForecast implements Serializable {

    private long dt;

    private Main main;

    private List<Weather> weather;

    private String dt_txt;

}

