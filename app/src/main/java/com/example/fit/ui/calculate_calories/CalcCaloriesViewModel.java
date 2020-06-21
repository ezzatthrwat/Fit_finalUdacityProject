package com.example.fit.ui.calculate_calories;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.fit.database.AppRepository;
import com.example.fit.model.UserDetails_Entry;

public class CalcCaloriesViewModel extends AndroidViewModel {

    private final LiveData<UserDetails_Entry> detailsEntryLiveData ;

    public CalcCaloriesViewModel(@NonNull Application application) {
        super(application);
        AppRepository appRepository = AppRepository.getInstance(application);
        detailsEntryLiveData = appRepository.getUserCaloriesInfo();
    }

    public LiveData<UserDetails_Entry> getUserCaloriesInfo(){
        return detailsEntryLiveData;
    }
}
