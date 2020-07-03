package com.example.carrental.initial.history;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrental.R;

public class HistoryrecyclerView extends RecyclerView.ViewHolder {
    public ImageView vehicles_image;
    public TextView vehicles_name,vehicles_status;
    public CardView vehicle_new_cardView;
    public RatingBar vehicle_new_rating;
    public HistoryrecyclerView(View view) {
        super(view);
        vehicles_image = view.findViewById(R.id.history_vehicles_image);
        vehicles_name = view.findViewById(R.id.history_vehicles_name);
        vehicles_status = view.findViewById(R.id.history_vehicles_status);
        vehicle_new_cardView = view.findViewById(R.id.history_cars_card);
        vehicle_new_rating = view.findViewById(R.id.history_vehicles_rate);
    }
}
