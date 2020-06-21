package com.example.fit.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "UserDetails")
public class UserDetails_Entry {
    @PrimaryKey(autoGenerate = true)
    private int id ;
    private int Age ;
    private int Height ;
    private int Weight ;
    private double MainCalories ;
    private double ToBurnHalf ;
    private double ToBurnOne ;
    private double ToGainHalf ;
    private double ToGainOne ;

    @Ignore
    public UserDetails_Entry(int age, int height, int weight, double mainCalories, double toBurnHalf, double toBurnOne, double toGainHalf, double toGainOne) {
        Age = age;
        Height = height;
        Weight = weight;
        MainCalories = mainCalories;
        ToBurnHalf = toBurnHalf;
        ToBurnOne = toBurnOne;
        ToGainHalf = toGainHalf;
        ToGainOne = toGainOne;
    }

    public UserDetails_Entry(int id, int Age, int Height, int Weight, double MainCalories, double ToBurnHalf, double ToBurnOne, double ToGainHalf, double ToGainOne) {
        this.id = id;
        this.Age = Age;
        this.Height = Height;
        this.Weight = Weight;
        this.MainCalories = MainCalories;
        this.ToBurnHalf = ToBurnHalf;
        this.ToBurnOne = ToBurnOne;
        this.ToGainHalf = ToGainHalf;
        this.ToGainOne = ToGainOne;
    }

    public int getId() {
        return id;
    }

    public int getAge() {
        return Age;
    }

    public int getHeight() {
        return Height;
    }

    public int getWeight() {
        return Weight;
    }

    public double getMainCalories() {
        return MainCalories;
    }

    public double getToBurnHalf() {
        return ToBurnHalf;
    }

    public double getToBurnOne() {
        return ToBurnOne;
    }

    public double getToGainHalf() {
        return ToGainHalf;
    }

    public double getToGainOne() {
        return ToGainOne;
    }

}
