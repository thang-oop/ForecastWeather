package com.thang.forecastweather.ui.displayNote;

import com.thang.forecastweather.model.weatherNote.Note;

import java.util.List;

public interface NoteInterface {
    void onSuccess(List<Note> notes);
    void onFailed(String error);
}
