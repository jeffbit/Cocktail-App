package com.example.jeff.mtbtrailapp.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeff.mtbtrailapp.Model.DrinkInfo;
import com.example.jeff.mtbtrailapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class DrinkOnClickActivity extends AppCompatActivity {

    private ImageView drinkIV;
    private TextView name, ingredients1, ingredients2, iba, glass, category, directions1, directions2, measurements;
    private FloatingActionButton addToFav;
    private DrinkInfo drink;
    private String drinkNameStr, drinkIdStr, drinkIngStr, drinkGlassStr, drinkCatStr, drinkInsStr, drinkIbaStr, drinkMeasureStr, userId;
    private FirebaseFirestore firebaseFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink_on_click);

        firebaseFirestore = FirebaseFirestore.getInstance();


        drinkIV = findViewById(R.id.drinkIVdocf);
        name = findViewById(R.id.drinkNameTVdocf);
        ingredients1 = findViewById(R.id.ingrendientsTVdocf);
        ingredients2 = findViewById(R.id.ingredientsListTVdocf);
        measurements = findViewById(R.id.measureListTVdocf);
        iba = findViewById(R.id.drinkIbaTVdocf);
        glass = findViewById(R.id.glassTVdocf);
        category = findViewById(R.id.categoryTVdocf);
        directions1 = findViewById(R.id.directionsTVdocf);
        directions2 = findViewById(R.id.directionsListTVdocf);
        addToFav = findViewById(R.id.addToFavoritesFABdocf);


        //passes intent from onclick adapterview
        Intent intent = getIntent();
        drink = intent.getParcelableExtra("drink");
        drinkIdStr = drink.getIdDrink();
        drinkNameStr = drink.getStrDrink();
        drinkCatStr = drink.getStrCategory();
        drinkIbaStr = drink.getStrIBA();
        drinkGlassStr = drink.getStrGlass();
        drinkInsStr = drink.getStrInstructions();
        drinkIngStr = drink.getStrIngredient1() + "\n"
                + drink.getStrIngredient2() + "\n"
                + drink.getStrIngredient3() + "\n"
                + drink.getStrIngredient4() + "\n"
                + drink.getStrIngredient5() + "\n"
                + drink.getStrIngredient6() + "\n"
                + drink.getStrIngredient7() + "\n"
                + drink.getStrIngredient8() + "\n"
                + drink.getStrIngredient9() + "\n"
                + drink.getStrIngredient10() + "\n"
                + drink.getStrIngredient11() + "\n"
                + drink.getStrIngredient12() + "\n"
                + drink.getStrIngredient13() + "\n"
                + drink.getStrIngredient14() + "\n"
                + drink.getStrIngredient15() + "\n";
        drinkMeasureStr = drink.getStrMeasure1() + "\n"
                + drink.getStrMeasure2() + "\n"
                + drink.getStrMeasure3() + "\n"
                + drink.getStrMeasure4() + "\n"
                + drink.getStrMeasure5() + "\n"
                + drink.getStrMeasure6() + "\n"
                + drink.getStrMeasure7() + "\n"
                + drink.getStrMeasure8() + "\n"
                + drink.getStrMeasure9() + "\n"
                + drink.getStrMeasure10() + "\n"
                + drink.getStrMeasure11() + "\n"
                + drink.getStrMeasure12() + "\n"
                + drink.getStrMeasure13() + "\n"
                + drink.getStrMeasure14() + "\n"
                + drink.getStrMeasure15() + "\n";


        Picasso.get().load(drink.getStrDrinkThumb())
                .placeholder(R.drawable.ic_menu_black_24dp)
                .into(drinkIV);
        name.setText(drinkNameStr);
        ingredients2.setText(drinkIngStr);
        iba.setText(drinkIbaStr);
        glass.setText(drinkGlassStr);
        category.setText(drinkCatStr);
        directions2.setText(drinkInsStr);
        measurements.setText(drinkMeasureStr);


        //adds drink to favorite list
        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToFavorites();


            }
        });


    }




    public void addToFavorites() {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Map<String, String> userMap = new HashMap<>();
        userMap.put("drinkName", drink.getStrDrink());
        userMap.put("drinkIv", drink.getStrDrinkThumb());
        userMap.put("drinkIng", drinkIngStr);
        userMap.put("drinkDirect", drinkInsStr);


        //adds a new document with generated ID
        firebaseFirestore.collection("users").document(userId).collection("favorites").document(drink.getIdDrink()).set(userMap);

    }


}
