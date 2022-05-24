package com.thang.forecastweather.ui.displayWeather7Day;
import com.thang.forecastweather.model.weather7Days.Weather7Day;
import com.thang.forecastweather.service.APIService;
import com.thang.forecastweather.service.Dataservice;

import lombok.Data;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Data
public class Weather7DayPresenter {
    private Weather7DayInterface weather7DayInterface;

    public Weather7DayPresenter(Weather7DayInterface weather7DayInterface) {
        this.weather7DayInterface = weather7DayInterface;
    }

    public void getDataByWeather7DayFragment(String city, String apiKey){
        Dataservice dataservice = APIService.getService();
        Call<Weather7Day> callback = dataservice.getDataWeather7Day(city, apiKey);
        callback.enqueue(new Callback<Weather7Day>() {
            @Override
            public void onResponse(Call<Weather7Day> call, Response<Weather7Day> response) {
                Weather7Day weather7day = response.body();
                if (weather7day != null){
                    weather7DayInterface.onSuccess(weather7day);
                } else {
                    weather7DayInterface.onFailed("Error");
                }
            }

            @Override
            public void onFailure(Call<Weather7Day> call, Throwable t) {

            }
        });
    }
}
