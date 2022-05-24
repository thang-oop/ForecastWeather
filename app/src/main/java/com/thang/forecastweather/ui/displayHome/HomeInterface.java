package com.thang.forecastweather.ui.displayHome;

import com.thang.forecastweather.model.weatherCurrent.WeatherCurrent;

public interface HomeInterface {
    void onSuccess(WeatherCurrent data);
    void onFailed(String error);
}
