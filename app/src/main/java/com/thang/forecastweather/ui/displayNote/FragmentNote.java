package com.thang.forecastweather.ui.displayNote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.thang.forecastweather.adapter.RecycleViewNoteAdapter;
import com.thang.forecastweather.databinding.FragmentNoteBinding;
import com.thang.forecastweather.db.SQLiteNoteOpenHelper;
import com.thang.forecastweather.model.weather7Days.ListForecast;
import com.thang.forecastweather.model.weatherNote.Note;

import java.util.List;

public class FragmentNote extends Fragment implements NoteInterface{
    private FragmentNoteBinding binding;
    private RecycleViewNoteAdapter adapter;
    private SQLiteNoteOpenHelper sqLiteQuyenGopOpenHelper;
    private NotePresenter notePresenter;
    private View view;

    public static final String TAG = FragmentNote.class.getName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqLiteQuyenGopOpenHelper = new SQLiteNoteOpenHelper(getActivity());
        notePresenter = new NotePresenter(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater,container,false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateView();
    }

    private void updateView() {
        List<Note> notes = sqLiteQuyenGopOpenHelper.getAllNote();
        updateDataToRecycleView(notes);
    }

    public void receiveDataFromFragment7Day(ListForecast lists) {
        setDataToSQLite(lists);
    }

    private void setDataToSQLite(ListForecast lists) {
        long date = lists.getDt();
        float tempMin = lists.getMain().getTemp_min();
        float tempMax = lists.getMain().getTemp_max();
        int humidity = lists.getMain().getHumidity();
        String icon = lists.getWeather().get(0).getIcon();
        Note note = new Note(date, icon, tempMin, tempMax, humidity);
        sentDataToNotePresenter(note);
    }

    private void sentDataToNotePresenter(Note note) {
        notePresenter.getListNote(note);
    }

    public void updateDataToRecycleView(List<Note> notes){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        binding.rcvNote.setLayoutManager(linearLayoutManager);
//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL);
//        binding.rcvNote.addItemDecoration(itemDecoration);
        adapter = new RecycleViewNoteAdapter(getActivity(), notes);
        binding.rcvNote.setAdapter(adapter);
    }

    @Override
    public void onSuccess(List<Note> notes) {
        updateDataToRecycleView(notes);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getActivity(),error, Toast.LENGTH_SHORT).show();
    }
}
