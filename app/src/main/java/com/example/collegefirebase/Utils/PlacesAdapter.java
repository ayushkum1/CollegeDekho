package com.example.collegefirebase.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegefirebase.Activites.PlacesView;
import com.example.collegefirebase.Model.NearByPlacesModel;
import com.example.collegefirebase.R;

import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesView> {

    Context context;
    List<NearByPlacesModel> placeslist;

    public PlacesAdapter(Context context, List<NearByPlacesModel> placeslist) {
        this.context = context;
        this.placeslist = placeslist;
    }

    @NonNull
    @Override
    public PlacesView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activity_places_view, parent, false);
        return new PlacesView(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacesView holder, int position) {
        holder.name.setText(placeslist.get(position).getName());
        holder.address.setText(placeslist.get(position).getAddress());
        holder.rating.setText(placeslist.get(position).getRating());
        holder.id.setText(placeslist.get(position).getId());
    }

    @Override
    public int getItemCount() {
        return placeslist.size();
    }
}
