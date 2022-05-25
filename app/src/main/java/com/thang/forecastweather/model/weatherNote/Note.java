package com.thang.forecastweather.model.weatherNote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Note {

    private int id;
    private long date;
    private String icon;
    private float temp_min;
    private float temp_max;
    private int humidity;
    private boolean isNote;

    public Note(long date, String icon, float temp_min, float temp_max, int humidity, boolean isNote) {
        this.date = date;
        this.icon = icon;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
        this.isNote = isNote;
    }

    public Note(long date, String icon, float temp_min, float temp_max, int humidity) {
        this.date = date;
        this.icon = icon;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.humidity = humidity;
    }
}
