package com.example.fit.ui.video_list;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fit.R;
import com.example.fit.ui.calculate_calories.CalculateCaloriesActivity;
import com.example.fit.ui.setting.SettingActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    AdView mAdView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);

        startFragment();
        initBannerAd();

    }

    private void initBannerAd() {
        MobileAds.initialize(this);
        mAdView = findViewById(R.id.adView);
        AdRequest myAdRequest = new AdRequest.Builder().build();
        mAdView.loadAd(myAdRequest);
    }

    private void startFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentVideosList fragmentVideosList = new FragmentVideosList();
        fragmentManager.beginTransaction()
                .add(R.id.ListContainer, fragmentVideosList)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == R.id.SettingMenu) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.CalculateCaloriesMenu){
            Intent intent = new Intent(this, CalculateCaloriesActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
