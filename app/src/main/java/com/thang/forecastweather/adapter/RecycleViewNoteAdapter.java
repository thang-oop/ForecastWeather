package com.thang.forecastweather.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thang.forecastweather.R;
import com.thang.forecastweather.databinding.ItemWeatherNoteBinding;
import com.thang.forecastweather.db.SQLiteNoteOpenHelper;
import com.thang.forecastweather.model.weatherNote.Note;
import com.thang.forecastweather.ui.displayNote.FragmentNote;
import com.thang.forecastweather.utils.KeyTemF;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecycleViewNoteAdapter extends RecyclerView.Adapter<RecycleViewNoteAdapter.MyViewHolder> {
    private Context context;
    private List<Note> notes;
    private FragmentNote fragmentNote;
    private SQLiteNoteOpenHelper db;
    private DecimalFormat df = new DecimalFormat("#");

    public RecycleViewNoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_note,parent,false);
        db = new SQLiteNoteOpenHelper(context);
        fragmentNote = new FragmentNote();
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (notes == null){
            return;
        }
        holder.bindData(position);
        holder.binding.imageNote.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert Delete");
                builder.setMessage("Are you sure delete");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.deleteNote(notes.get(position).getId());
                        notes.remove(notes.get(position));
                        notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (notes != null){
            return notes.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemWeatherNoteBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemWeatherNoteBinding.bind(itemView);
        }

        private void bindData(int position){
            Note note = notes.get(position);
            Date date = new Date(Long.valueOf(note.getDate()) * 1000L);
            SimpleDateFormat sp = new SimpleDateFormat("EE");
            SimpleDateFormat spTime = new SimpleDateFormat("HH:mm");
            binding.textviewHumidityNote.setText(String.valueOf(note.getHumidity()));
            binding.textviewTempminNote.setText(String.valueOf(df.format(note.getTemp_min()- KeyTemF.TEMF)));
            binding.textviewTempmaxNote.setText(String.valueOf(df.format(note.getTemp_max()- KeyTemF.TEMF)));
            binding.textviewDateNote.setText(sp.format(date));
            binding.textviewHourNote.setText(spTime.format(date));
            String icon = note.getIcon();
            Glide.with(context).load("http://openweathermap.org/img/wn/" + icon + ".png").into(binding.imagewviewIconNote);
        }
    }
}
