package com.example.fit.ui.setting;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.fit.R;
import com.example.fit.ui.registration.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        setTitle(R.string.SettingTitle);
        ActionBar actionBar = getSupportActionBar() ;
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        startSettingFragment();
    }

    private void startSettingFragment() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        SettingFragment fragmentVideosList = new SettingFragment();
        fragmentManager.beginTransaction()
                .add(R.id.settingFragContainer, fragmentVideosList)
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    public void logOutClickListener(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        finishAffinity();
        startActivity(intent);
    }
}
