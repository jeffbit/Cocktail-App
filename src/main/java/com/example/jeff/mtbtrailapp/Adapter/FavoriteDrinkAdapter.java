package com.example.jeff.mtbtrailapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jeff.mtbtrailapp.Model.Drink;
import com.example.jeff.mtbtrailapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavoriteDrinkAdapter extends RecyclerView.Adapter<FavoriteDrinkAdapter.FavoriteDrinkViewHolder> {

    private ArrayList<Drink> drinks;
    String drinkId;

    public FavoriteDrinkAdapter(ArrayList<Drink> drinks) {
        this.drinks = drinks;
    }

    @NonNull
    @Override
    public FavoriteDrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_favoritedrink_row, viewGroup, false);

        return new FavoriteDrinkAdapter.FavoriteDrinkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteDrinkViewHolder favoriteDrinkViewHolder, int i) {
        Drink drink = drinks.get(i);

        drinkId = drink.getDrinkId();
        favoriteDrinkViewHolder.drinkName.setText(drink.getDrinkName());
        favoriteDrinkViewHolder.drinkIns.setText(drink.getDrinkDirect());
        favoriteDrinkViewHolder.drinkIng.setText(drink.getDrinkIng());
        Picasso.get().load(drink.getDrinkIv())
                .placeholder(R.drawable.ic_menu_black_24dp)
                .into(favoriteDrinkViewHolder.drinkPic);


    }


    @Override
    public int getItemCount() {
        return drinks.size();
    }

    public class FavoriteDrinkViewHolder extends RecyclerView.ViewHolder {
        private TextView drinkName, drinkIng, drinkIns;
        private ImageView drinkPic;
        private Button removeBtn;

        public FavoriteDrinkViewHolder(@NonNull View itemView) {
            super(itemView);

            drinkName = itemView.findViewById(R.id.drinkNameTVFD);
            drinkIng = itemView.findViewById(R.id.drinkIngTVFD);
            drinkIns = itemView.findViewById(R.id.drinkInstructionsTVFD);
            drinkPic = itemView.findViewById(R.id.drinkRowIVFD);
            removeBtn = itemView.findViewById(R.id.deleteDrinkBtnFD);


            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                    //removes currently selected item
                    firebaseFirestore.collection("users").document(userId).collection("favorites").document(drinkId).delete();
                    drinks.remove(getAdapterPosition());
                    notifyDataSetChanged();



                }
            });

        }
    }
}
