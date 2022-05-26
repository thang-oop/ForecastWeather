package com.thang.forecastweather.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thang.forecastweather.R;
import com.thang.forecastweather.databinding.ItemWeather7dayBinding;
import com.thang.forecastweather.db.SQLiteNoteOpenHelper;
import com.thang.forecastweather.model.weather7Days.ListForecast;
import com.thang.forecastweather.model.weatherNote.Note;
import com.thang.forecastweather.utils.KeyTemF;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class RecycleViewWeather7DayAdapter extends RecyclerView.Adapter<RecycleViewWeather7DayAdapter.MyViewHolder> {
    private Context context;
    private List<ListForecast> list_Lists ;
    private senDataToActiviTy senDataToActiviTy;
    private DecimalFormat df = new DecimalFormat("#");
    private SQLiteNoteOpenHelper db;

    public RecycleViewWeather7DayAdapter(List<ListForecast> list, Context context){
        this.list_Lists = list;
        this.context = context;
    }

    public interface senDataToActiviTy{
        void senData(ListForecast lists);
    }

    public RecycleViewWeather7DayAdapter(senDataToActiviTy senDataToActiviTy){
        this.senDataToActiviTy = senDataToActiviTy;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_weather_7day,parent,false);
        senDataToActiviTy = (senDataToActiviTy) context;
        db = new SQLiteNoteOpenHelper(context);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (list_Lists == null){
            return;
        }
        holder.bindData(position);
        holder.binding.imageNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.binding.imageNote.getTag() != null && Integer.parseInt(holder.binding.imageNote.getTag().toString()) == R.drawable.ic_note_change) {
                    Toast.makeText(context, "Duplicate", Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Alert Add");
                builder.setMessage("Are you sure add forecast weather ");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        senDataToActiviTy.senData(list_Lists.get(position));
                        holder.binding.imageNote.setImageResource(R.drawable.ic_note_change);
                        holder.binding.imageNote.setTag(R.drawable.ic_note_change);
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
        if (list_Lists != null){
            return list_Lists.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private ItemWeather7dayBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemWeather7dayBinding.bind(itemView);
        }

        public void bindData(int position) {
            ListForecast lists = list_Lists.get(position);
            Date date = new Date(Long.valueOf(lists.getDt()) * 1000L);
            SimpleDateFormat sp = new SimpleDateFormat("EE");
            SimpleDateFormat spTime = new SimpleDateFormat("HH:mm");
            binding.textviewHumidity16day.setText(String.valueOf(lists.getMain().getHumidity()));
            binding.textviewTempmin16day.setText(String.valueOf(df.format(lists.getMain().getTemp_min()- KeyTemF.TEMF)));
            binding.textviewTempmax16day.setText(String.valueOf(df.format(lists.getMain().getTemp_max()- KeyTemF.TEMF)));
            binding.textviewDate16day.setText(sp.format(date));
            binding.textviewHour.setText(spTime.format(date));
            binding.imageNote.setImageResource(checkIcon(lists.getDt()) ? R.drawable.ic_note_change : R.drawable.ic_note);
            String icon = "";
            if (lists.getWeather() != null) icon = lists.getWeather().get(0).getIcon();

            Glide.with(context).load("http://openweathermap.org/img/wn/" + icon + ".png").into(binding.imagewviewIcon16day);
        }

        public boolean checkIcon(long date) {
            List<Note> list = db.getAllNote();

            for (Note note : list) {
                if (note.getDate() == date) return true;
            }

            return false;
        }
    }

}
