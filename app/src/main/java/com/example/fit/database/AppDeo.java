package com.example.fit.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fit.model.UserDetails_Entry;

@Dao
public interface AppDeo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserCaloriesInfo(UserDetails_Entry userDetails_entry);

    @Query("SELECT * FROM UserDetails")
    LiveData<UserDetails_Entry> getUserCaloriesInfo();
}
