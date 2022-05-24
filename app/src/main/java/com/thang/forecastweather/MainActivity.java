package com.thang.forecastweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.thang.forecastweather.adapter.ViewPagerAdapter;
import com.thang.forecastweather.databinding.ActivityMainBinding;
import com.thang.forecastweather.ui.displayHome.FragmentHome;

public class MainActivity extends AppCompatActivity {

    private ViewPagerAdapter adapter;
    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new FragmentHome(), "HOME");
        adapter.addFragment(new FragmentHome(), "7DAY");
        adapter.addFragment(new FragmentHome(), "NOTE");
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.setOffscreenPageLimit(0); //load du lieu cho 0 tap tiep thep
        binding.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    Fragment fragment_weather7day = adapter.getFragmentByPosition(position);
                    Fragment fragment_home = adapter.getFragmentByPosition(0);
                    if (fragment_home instanceof FragmentHome) {
                        FragmentHome homeFragment = (FragmentHome) fragment_home;
                        String cityName = homeFragment.getCityName();
//                        if (fragment_weather7day instanceof Weather7DayFragment){
//                            Weather7DayFragment weather_7dayFragment = (Weather7DayFragment) fragment_weather7day;
//                            weather_7dayFragment.setCityName(cityName);
//                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        binding.tapLayout.setupWithViewPager(binding.viewPager);
        binding.tapLayout.getTabAt(0).setIcon(R.drawable.ic_baseline_home_24);
        binding.tapLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_next_week_24);
        binding.tapLayout.getTabAt(2).setIcon(R.drawable.ic_note);
    }
}