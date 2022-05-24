package com.thang.forecastweather.ui.displayWeather7Day;


import com.thang.forecastweather.model.weather7Days.Weather7Day;

public interface Weather7DayInterface {
    void onSuccess(Weather7Day data);
    void onFailed(String error);
}
