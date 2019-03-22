package com.example.jeff.mtbtrailapp.UI.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.jeff.mtbtrailapp.Adapter.ReportAdapter;
import com.example.jeff.mtbtrailapp.Model.Drink;
import com.example.jeff.mtbtrailapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ReportActivity extends AppCompatActivity {

    private TextView userTitle, email, userDateCreated, lastLogin, usersFavTitle;
    private RecyclerView recyclerView;
    private ReportAdapter reportAdapter;
    ArrayList<Drink> reportDrinks;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        reportDrinks = new ArrayList<>();
        userTitle = findViewById(R.id.userTitleReportTV);
        email = findViewById(R.id.emailReportTV);
        userDateCreated = findViewById(R.id.userDateCreatedTV);
        lastLogin = findViewById(R.id.lastLoginTV);
        usersFavTitle = findViewById(R.id.usersFavDrinkTitleTV);


        firebaseFirestore = FirebaseFirestore.getInstance();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String userEmailStr = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        Long userDateLong = FirebaseAuth.getInstance().getCurrentUser().getMetadata().getCreationTimestamp();
        Long lastLoginLong = FirebaseAuth.getInstance().getCurrentUser().getMetadata().getLastSignInTimestamp();

        //converts long date to string
        Format format = new SimpleDateFormat("yyyy MM dd HH:mm:ss");
        String creationDate = format.format(userDateLong);
        String loginDate = format.format(lastLoginLong);

        email.setText("Email: " + userEmailStr);
        userDateCreated.setText("User created on : " + creationDate);
        lastLogin.setText("User last login: " + loginDate);

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

                        reportDrinks.add(d);
                        generateData(reportDrinks);
                    }
                }


            }


        });

    }

    public void generateData(ArrayList<Drink> drinks) {
        recyclerView = findViewById(R.id.reportRecyclerView);
        reportAdapter = new ReportAdapter(drinks);
        reportAdapter.notifyDataSetChanged();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ReportActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(reportAdapter);
    }
}


