package com.example.jeff.mtbtrailapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.jeff.mtbtrailapp.Adapter.FavoriteDrinkAdapter;
import com.example.jeff.mtbtrailapp.Model.Drink;
import com.example.jeff.mtbtrailapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FavoriteDrinksFrameActivity extends AppCompatActivity {

    ArrayList<Drink> drinks;
    private RecyclerView recyclerView;
    private FavoriteDrinkAdapter favoriteDrinkAdapter;
    private FirebaseFirestore firebaseFirestore;
    private String userId;
    private FloatingActionButton reportFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_drinks_frame);
        //create new arraylist
        drinks = new ArrayList<>();

        reportFab = findViewById(R.id.reportFAB);
        reportFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReportActivity.class);
                startActivity(intent);
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        //gets data favorited data from firebase by userId
        CollectionReference users = firebaseFirestore.collection("users").document(userId).collection("favorites");
        users.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {

                    for (DocumentSnapshot snapshot : task.getResult()) {
                        Drink d = snapshot.toObject(Drink.class);
                        d.setDrinkId(snapshot.getId());
                        d.setDrinkName(snapshot.get("drinkName").toString());
                        d.setDrinkIv(snapshot.get("drinkIv").toString());
                        d.setDrinkIng(snapshot.get("drinkIng").toString());
                        d.setDrinkDirect(snapshot.get("drinkDirect").toString());
                        drinks.add(d);
                        generateData(drinks);
                    }
                }

            }

        });


    }

    public void generateData(ArrayList<Drink> drinks) {
        recyclerView = findViewById(R.id.favoriteDrinkRecyclerView);
        favoriteDrinkAdapter = new FavoriteDrinkAdapter(drinks);
        favoriteDrinkAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(favoriteDrinkAdapter);

    }

    public void generateReport() {
        for (Drink drink : drinks) {
        }
    }


}
