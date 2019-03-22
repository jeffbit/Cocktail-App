package com.example.jeff.mtbtrailapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DrinkList {

    @SerializedName("drinks")
    private ArrayList<DrinkInfo> drinks;

    public ArrayList<DrinkInfo> getDrinks() {
        return drinks;
    }

    public void setDrinks(ArrayList<DrinkInfo> drinks) {
        this.drinks = drinks;
    }
}
