package com.thang.forecastweather.service;

import com.thang.forecastweather.model.weather7Days.Weather7Day;
import com.thang.forecastweather.model.weatherCurrent.WeatherCurrent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Dataservice {  //gui phuong thuc len Server va lay du lieu ve(de o dang interface)
    @GET("data/2.5/weather")
    Call<WeatherCurrent> getDataCurrentWeather(@Query("q") String city, @Query("appid") String apiKey);

    @GET("data/2.5/forecast")
    Call<Weather7Day> getDataWeather7Day(@Query("q") String city, @Query("appid") String apiKey);
}
