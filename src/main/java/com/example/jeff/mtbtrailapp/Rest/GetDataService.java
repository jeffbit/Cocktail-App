package com.example.jeff.mtbtrailapp.Rest;

import com.example.jeff.mtbtrailapp.Model.DrinkInfo;
import com.example.jeff.mtbtrailapp.Model.DrinkList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("search.php")
    Call<DrinkList> getDrinksByName(@Query("s") String strDrink);

    @GET("lookup.php")
    Call<DrinkInfo> getDrinkById(@Query("i") String idDrink);


}
