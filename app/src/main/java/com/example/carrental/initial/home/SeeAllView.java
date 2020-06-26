package com.example.carrental.initial.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carrental.R;

public class SeeAllView extends RecyclerView.ViewHolder {
     ImageView see_all_vehicles_image;
     TextView see_all_vehicles_name,see_all_vehicles_number;
     CardView see_all_vehicle_new_cardView;
     RatingBar see_all_rating;

    public SeeAllView(@NonNull View itemView) {
        super(itemView);
        see_all_vehicles_image = itemView.findViewById(R.id.see_all_vehicles_image);
        see_all_vehicles_name = itemView.findViewById(R.id.see_all_vehicles_name);
        see_all_vehicles_number = itemView.findViewById(R.id.see_all_vehicles_number);
        see_all_vehicle_new_cardView = itemView.findViewById(R.id.see_all_cars_card);
        see_all_rating = itemView.findViewById(R.id.see_all_vehicles_rate);
    }
}
