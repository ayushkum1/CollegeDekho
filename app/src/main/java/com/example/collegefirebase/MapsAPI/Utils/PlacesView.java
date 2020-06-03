package com.example.collegefirebase.MapsAPI.Utils;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegefirebase.R;

public class PlacesView extends RecyclerView.ViewHolder {

    public TextView name, address, id, rating;

    public PlacesView(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name);
        address = itemView.findViewById(R.id.address);
        rating = itemView.findViewById(R.id.rating);
        id = itemView.findViewById(R.id.place_id);
    }
}
