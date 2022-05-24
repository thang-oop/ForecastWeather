package com.thang.forecastweather.ui.displayHome;

import android.util.Log;

import com.thang.forecastweather.model.weatherCurrent.WeatherCurrent;
import com.thang.forecastweather.service.APIService;
import com.thang.forecastweather.service.Dataservice;

import lombok.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Data
public class HomePresenter {

    private HomeInterface homeInterface;

    public HomePresenter(HomeInterface homeInterface) {
        this.homeInterface = homeInterface;
    }

    public void getDataByFragmentHome(String city, String apiKey){
        Dataservice dataservice = APIService.getService();
        Call<WeatherCurrent> callback = dataservice.getDataCurrentWeather(city, apiKey);
        callback.enqueue(new Callback<WeatherCurrent>() {
            @Override
            public void onResponse(Call<WeatherCurrent> call, Response<WeatherCurrent> response) {
                WeatherCurrent weatherCurrent = response.body();
                if (weatherCurrent != null){
                    homeInterface.onSuccess(weatherCurrent);
                } else {
                    homeInterface.onFailed("Nhập đúng tên thành phố (ví dụ: Tokyo)");
                }
            }

            @Override
            public void onFailure(Call<WeatherCurrent> call, Throwable t) {
                Log.d("error", t.toString());
            }
        });
    }

}
