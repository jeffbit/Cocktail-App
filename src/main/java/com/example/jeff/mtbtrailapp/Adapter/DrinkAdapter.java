package com.example.jeff.mtbtrailapp.Adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeff.mtbtrailapp.Model.DrinkInfo;
import com.example.jeff.mtbtrailapp.R;
import com.example.jeff.mtbtrailapp.UI.Activity.DrinkOnClickActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder> {

    private ArrayList<DrinkInfo> drinkList;

    public DrinkAdapter(ArrayList<DrinkInfo> drink) {
        this.drinkList = drink;

    }

    @NonNull
    @Override
    public DrinkViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_drink_row, viewGroup, false);

        return new DrinkViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull DrinkViewHolder drinkViewHolder, int i) {

        DrinkInfo drink = drinkList.get(i);
        drinkViewHolder.drinkName.setText(drink.getStrDrink());


        //uses picasso to put thumbnail image in imageview
        Picasso.get().load(drinkList.get(i).getStrDrinkThumb())
                .placeholder(R.drawable.ic_menu_black_24dp)
                .into(drinkViewHolder.image);


    }


    @Override
    public int getItemCount() {

        return drinkList.size();
    }

    class DrinkViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView drinkName;

        public DrinkViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.drinkRowIV);
            drinkName = itemView.findViewById(R.id.drinkNameTVdocf);


            //onclicklistener for each item in recyclerview
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //load new fragment with information on currently clicked trail
                    DrinkInfo drinkClicked = drinkList.get(getAdapterPosition());
                    Intent intent = new Intent(v.getContext(), DrinkOnClickActivity.class);
                    intent.putExtra("drink", drinkClicked);
                    v.getContext().startActivity(intent);

                    Toast.makeText(v.getContext(), drinkClicked.getStrGlass(), Toast.LENGTH_SHORT).show();


                }
            });


        }


    }
}
