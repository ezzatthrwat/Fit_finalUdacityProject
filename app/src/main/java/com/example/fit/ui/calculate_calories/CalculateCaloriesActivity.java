package com.example.fit.ui.calculate_calories;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.fit.R;
import com.example.fit.app_widget.CaloriesInfoWidgetProvider;
import com.example.fit.database.AppRepository;
import com.example.fit.databinding.ActivityCalculateCaloriesBinding;
import com.example.fit.model.UserDetails_Entry;

import java.util.Objects;


public class CalculateCaloriesActivity extends AppCompatActivity {

   private ActivityCalculateCaloriesBinding mActivityCalculateCaloriesBinding;

    private double BMR, exercise , DRI;
    private int age;
    private int weight;
    private int height;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityCalculateCaloriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_calculate_calories) ;
        setTitle(getString(R.string.CalculateCaloriesTitle));
        ActionBar actionBar = getSupportActionBar() ;
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        initViewModel();
        initActivitySpinner();
        CalculateCaloriesButton();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
    private void initViewModel() {
        CalcCaloriesViewModel viewModel = new ViewModelProvider(this).get(CalcCaloriesViewModel.class);

        viewModel.getUserCaloriesInfo().observe(this, userDetails_entry -> {
            if (userDetails_entry != null){
                if (userDetails_entry.getAge() != 0 && userDetails_entry.getHeight()!= 0 && userDetails_entry.getWeight() != 0) {
                    mActivityCalculateCaloriesBinding.CaloriesDetails.CaloriesDetailsCard.setVisibility(View.VISIBLE);
                    mActivityCalculateCaloriesBinding.setUserCaloriesInfo(userDetails_entry);
                    mActivityCalculateCaloriesBinding.CaloriesDetails.setUserCaloriesInfo(userDetails_entry);
                }
            }else {
                mActivityCalculateCaloriesBinding.AgeInput.setText("");
                mActivityCalculateCaloriesBinding.HeightInput.setText("");
                mActivityCalculateCaloriesBinding.WeightInput.setText("");
            }
        });

    }
    private void initActivitySpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,
                        getResources().getStringArray(R.array.ActivitySpinnerStrings));

        mActivityCalculateCaloriesBinding.ActivitySpinner.setAdapter(adapter);

        mActivityCalculateCaloriesBinding.ActivitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String exerciseType = (String) parent.getSelectedItem();
                String[] arrayString = getResources().getStringArray(R.array.ActivitySpinnerStrings);

                if(exerciseType.equals(arrayString[0])){
                    exercise = 1 ;
                }else if (exerciseType.equals(arrayString[1])){
                    exercise = 1.2 ;
                }else if ((exerciseType.equals(arrayString[2]))){
                    exercise = 1.375 ;
                }else if ((exerciseType.equals(arrayString[3]))){
                    exercise = 1.55 ;
                }else if ((exerciseType.equals(arrayString[4]))){
                    exercise = 1.725 ;
                }else if ((exerciseType.equals(arrayString[5]))){
                    exercise = 1.9 ;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void CalculateCaloriesButton(){
        mActivityCalculateCaloriesBinding.CalculateCaloriesButton.setOnClickListener(v -> {
            if (editTextValidation()){
                age = Integer.parseInt(Objects.requireNonNull(mActivityCalculateCaloriesBinding.AgeInput.getText()).toString());
                weight = Integer.parseInt(Objects.requireNonNull(mActivityCalculateCaloriesBinding.WeightInput.getText()).toString());
                height = Integer.parseInt(Objects.requireNonNull(mActivityCalculateCaloriesBinding.HeightInput.getText()).toString());
                BMR = ((10 * weight) + (6.25 * height)) -5 * age + 5;
                DRI = BMR * exercise ;
                if(DRI > 0) {
                    calculateNeeds();
                } else {
                    age = 0;
                    weight = 0;
                    height = 0;
                    BMR = 0;
                    exercise = 0;
                }
            }
        });
    }
    private boolean editTextValidation(){
        boolean Valid = true;
        if (TextUtils.isEmpty(mActivityCalculateCaloriesBinding.AgeInput.getText())){
            Valid = false ;
            mActivityCalculateCaloriesBinding.AgeInput.setError(getString(R.string.AgeError));
        }else if (TextUtils.isEmpty(mActivityCalculateCaloriesBinding.HeightInput.getText())){
            Valid = false;
            mActivityCalculateCaloriesBinding.HeightInput.setError(getString(R.string.HeightError));
        }else if (TextUtils.isEmpty(mActivityCalculateCaloriesBinding.WeightInput.getText())){
            Valid = false;
            mActivityCalculateCaloriesBinding.WeightInput.setError(getString(R.string.WeightError));
        }
        return Valid ;
    }
    private void calculateNeeds(){
        renderUI();
        notifyWidget();
        insertInfoToDatabase();
    }
    private void insertInfoToDatabase() {
        UserDetails_Entry entry = new UserDetails_Entry(age, height, weight, DRI, (DRI - 500), (DRI - 1000), (DRI + 500), (DRI  + 1000));
        AppRepository.getInstance(this).addUserCaloriesInfo(entry);
    }
    private void notifyWidget(){
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CaloriesInfoWidgetProvider.class));
        String widgetTextString = getCaloriesInfoTextsToWidget() ;
        CaloriesInfoWidgetProvider.updateWidget(this, appWidgetManager, appWidgetIds, widgetTextString);
    }
    private void renderUI(){
        mActivityCalculateCaloriesBinding.CaloriesDetails.CaloriesDetailsCard.setVisibility(View.VISIBLE);
        mActivityCalculateCaloriesBinding.CaloriesDetails.maintain.setText(String.valueOf(DRI));
        mActivityCalculateCaloriesBinding.CaloriesDetails.toLose.setText(String.valueOf(DRI - 500));
        mActivityCalculateCaloriesBinding.CaloriesDetails.toLose2.setText( String.valueOf(DRI - 1000));
        mActivityCalculateCaloriesBinding.CaloriesDetails.toGain.setText(String.valueOf(DRI + 500));
        mActivityCalculateCaloriesBinding.CaloriesDetails.toGain2.setText(String.valueOf(DRI  + 1000));
    }
    private String getCaloriesInfoTextsToWidget() {
        return getString(R.string.you_need) +" "+ DRI +" "+ getString(R.string.calories_day_to_maintain_your_weight) + "\n" +
                getString(R.string.you_need) +" "+ (DRI - 500)+" "+ getString(R.string.calories_day_to_lose_0_5_kg_per_week) + "\n" +
                getString(R.string.you_need) +" "+(DRI - 1000)+" "+ getString(R.string.calories_day_to_lose_1_kg_per_week) + "\n" +
                getString(R.string.you_need) +" "+ (DRI + 500)+" "+ getString(R.string.calories_day_to_gain_0_5_kg_per_week) + "\n" +
                getString(R.string.you_need) +" "+ (DRI + 1000)+" "+ getString(R.string.calories_day_to_gain_1_kg_per_week);
    }
}
