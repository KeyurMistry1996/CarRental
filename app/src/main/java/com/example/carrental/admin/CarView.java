package com.example.carrental.admin;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrental.R;

public class CarView extends RecyclerView.ViewHolder {
    public ImageView vehicles_image;
    public TextView vehicles_name,vehicles_number;
    public CardView vehicle_popular_cardView;
    public RatingBar vehicle_popular_rating;
    public CarView(@NonNull View itemView) {
        super(itemView);
        vehicles_image = itemView.findViewById(R.id.see_all_vehicles_image);
        vehicles_name = itemView.findViewById(R.id.see_all_vehicles_name);
        vehicles_number = itemView.findViewById(R.id.see_all_vehicles_number);
        vehicle_popular_cardView = itemView.findViewById(R.id.see_all_cars_card);
        vehicle_popular_rating = itemView.findViewById(R.id.see_all_vehicles_rate);
    }
}
