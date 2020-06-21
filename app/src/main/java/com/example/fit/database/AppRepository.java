package com.example.fit.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.fit.AppExecutors;
import com.example.fit.model.UserDetails_Entry;

public class AppRepository {
    private static AppRepository ourInstance;
    private final AppDatabase mDb;


    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
    }

    public static AppRepository getInstance(Context context ) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    public void addUserCaloriesInfo(UserDetails_Entry userDetails_entry){
        AppExecutors.getInstance().diskIO().execute(() -> mDb.appDeo().insertUserCaloriesInfo(userDetails_entry));
    }

    public LiveData<UserDetails_Entry> getUserCaloriesInfo(){
       return  mDb.appDeo().getUserCaloriesInfo();
    }
}
