package com.example.jeff.mtbtrailapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.jeff.mtbtrailapp.Model.Drink;
import com.example.jeff.mtbtrailapp.R;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {


    List<Drink> report;

    public ReportAdapter(List<Drink> report) {
        this.report = report;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.fragment_report_row, viewGroup, false);
        return new ReportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder reportViewHolder, int i) {
        Drink drink = report.get(i);

        reportViewHolder.drinkId.setText("Drink Id: " + drink.getDrinkId());
        reportViewHolder.drinkName.setText("Drink Name: " + drink.getDrinkName());

    }


    @Override
    public int getItemCount() {
        return report.size();
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {

        private TextView drinkId, drinkName;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);

            drinkId = itemView.findViewById(R.id.drinkIdReportTV);
            drinkName = itemView.findViewById(R.id.drinkNameReportTV);


        }
    }
}
