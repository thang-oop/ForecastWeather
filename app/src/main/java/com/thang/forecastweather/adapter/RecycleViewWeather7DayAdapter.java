package com.thang.forecastweather.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.thang.forecastweather.R;
import com.thang.forecastweather.databinding.ItemWeather7dayBinding;
import com.thang.forecastweather.model.weather7Days.ListForecast;
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
                senDataToActiviTy.senData(list_Lists.get(position));
                holder.binding.imageNote.setImageResource(R.drawable.ic_note_change);
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
            SimpleDateFormat spTime = new SimpleDateFormat("HH:MM ");
            binding.textviewHumidity16day.setText(String.valueOf(lists.getMain().getHumidity()));
            binding.textviewTempmin16day.setText(String.valueOf(df.format(lists.getMain().getTemp_min()- KeyTemF.TEMF)));
            binding.textviewTempmax16day.setText(String.valueOf(df.format(lists.getMain().getTemp_max()- KeyTemF.TEMF)));
            binding.textviewDate16day.setText(sp.format(date));
            binding.textviewHour.setText(lists.getDt_txt().split(" ")[1]);
            String icon = "";
            if (lists.getWeather() != null) icon = lists.getWeather().get(0).getIcon();

            Glide.with(context).load("http://openweathermap.org/img/wn/" + icon + ".png").into(binding.imagewviewIcon16day);
        }
    }

}
